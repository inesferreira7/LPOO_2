package com.tq.doodle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.glass.ui.EventLoop;
import com.sun.glass.ui.View;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.Scenes.Hud;
import com.tq.doodle.Sprites.Coin;
import com.tq.doodle.Sprites.Doodle;
import com.tq.doodle.Sprites.Platform;
import com.tq.doodle.Tools.B2WorldCreator;
import com.tq.doodle.Tools.WorldContactListener;

import java.util.ArrayList;

/**
 * Created by Inês on 01/06/2016.
 */
public class PlayScreen implements Screen, InputProcessor {

    private DoodleJump game;
    private TextureAtlas atlas;
    private Doodle player;
    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private MapProperties prop;
    private Platform plat;
    private Music music;
    private Sound dough;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Coin coin;
    private Texture a;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private int mapWidth;
    private int mapHeight;
    private int finalScore;

    private final static Vector2 base = new Vector2(0, 0);

    //public ArrayList<Body> destroyNextUpdate;

    public PlayScreen(DoodleJump game) {
        this.game = game;
        atlas = new TextureAtlas("Jump.pack");
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false);
        gamePort = new FitViewport(DoodleJump.V_WIDTH / DoodleJump.PPM, DoodleJump.V_HEIGHT / DoodleJump.PPM, gamecam);
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map = maploader.load("teste.tmx");
        prop = map.getProperties();
        renderer = new OrthogonalTiledMapRenderer(map, 1 / DoodleJump.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);

        b2dr = new Box2DDebugRenderer();
        player = new Doodle(world, this);

        mapWidth = prop.get("width", Integer.class).intValue() * prop.get("tilewidth", Integer.class).intValue();
        mapHeight = prop.get("height", Integer.class).intValue() * prop.get("tileheight", Integer.class).intValue();

        plat = new Platform(world, this);

        new B2WorldCreator(world, map);

        Gdx.input.setInputProcessor(this);

        if (game.getMusic() == true && game.getSounds() == true) {
            music = Gdx.audio.newMusic(Gdx.files.internal("background_music.mp3"));
            music.setLooping(true);
            music.setVolume(1f);
            music.play();
            dough = Gdx.audio.newSound(Gdx.files.internal("simpson.ogg"));
        }

        Texture t = new Texture("Coin.png");
        coin = new Coin(new TextureRegion(t), 8, 0.6f, this, world);
        a = new Texture("Coin.png");


    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt) {

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            int cursor_x = Gdx.input.getX();
            int cursor_y = Gdx.input.getY();
            if (cursor_x >= 50 && cursor_x < 180) {
                if (cursor_y < 100 && cursor_y > 30) {
                    game.setScreen(new PauseScreen(this, game));
                    if (music != null) music.stop();
                    if (dough != null) dough.stop();

                }
            }
        }

        if (player.getWin() == true){
            game.setScreen(new WinScreen(game));
        }
    }

    public void update(float dt) {
        handleInput(dt);
/*
        for (Body toDelete: destroyNextUpdate){
            world.destroyBody(toDelete);
        }

*/
        world.step(1 / 60f, 6, 2);
        player.update(dt);
        gamecam.update();
        renderer.setView(gamecam);
        hud.update(dt);
        collisions();
        coin.update(dt);
    }

    @Override
    public void render(float delta) {

        update(delta);

        //clear the game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float cameraY = player.b2body.getPosition().y;
        if (cameraY < gamePort.getWorldHeight() / 2)
            cameraY = gamePort.getWorldHeight() / 2;
        else if (cameraY > mapHeight - gamePort.getWorldHeight() / 2)
            cameraY = mapHeight - gamePort.getWorldHeight() / 2;
        else if((mapHeight - 400)/DoodleJump.PPM < cameraY){
            cameraY = (mapHeight-400) / DoodleJump.PPM;

        }


        gamecam.position.set(gamePort.getWorldWidth() / 2, cameraY, 0);
        gamecam.update();
        renderer.setView(gamecam);

        //render game map
        renderer.render();

        //renderer our Box2DDebugLines
        //b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        plat.render(game.batch);
        coin.render(game.batch);
        //game.batch.draw(coin.getFrame(), 50/DoodleJump.PPM, 50/DoodleJump.PPM, coin.getFrame().getRegionWidth()/DoodleJump.PPM, coin.getFrame().getRegionHeight()/DoodleJump.PPM);
        game.batch.end();

        //Set our batch to now draw what the Hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        music.dispose();
        coin.dispose();
        plat.dispose();
        dough.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (player.b2body.getLinearVelocity() != base) {
            player.b2body.setLinearVelocity(0, 0);
        }

        if (screenX < DoodleJump.V_WIDTH / 2) {
            player.jumpRight();
            player.flipDoodle(player.kk, screenX);
        }
        if (screenX > DoodleJump.V_WIDTH / 2) {
            player.jumpLeft();
            player.flipDoodle(player.kk, screenX);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void collisions() {
        long endPauseTime = 0;

        for (int i = 0; i < plat.getRectangles().size; i++) {

            //Condiçao de perder
            if (player.collides(plat.getRectangles().get(i))) {

                if (game.sounds == true) dough.play(); //no commit nao ta a aparecer

                endPauseTime = System.currentTimeMillis() + (1 * 1000);

                while (System.currentTimeMillis() < endPauseTime) {

                }
                game.setScreen(new GameOverScreen(game));
                if (music != null) music.stop();
                if (dough != null) dough.stop();
            }
        }
    }
}

