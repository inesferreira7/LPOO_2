package com.tq.doodle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.Scenes.Hud;
import com.tq.doodle.Sprites.Doodle;
import com.tq.doodle.Tools.B2WorldCreator;

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

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private int mapWidth;
    private int mapHeight;

    public final static Vector2 GRAVITY = new Vector2(0, -10);
    private final static int JUMP_HEIGHT_MODIFIER = 50;
    private final static int JUMP_WIDTH_MODIFIER = 30;



    public PlayScreen(DoodleJump game){
        this.game= game;
        atlas = new TextureAtlas("Jump.pack");
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(DoodleJump.V_WIDTH/DoodleJump.PPM, DoodleJump.V_HEIGHT/DoodleJump.PPM,gamecam);
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        map = maploader.load("test.tmx");
        prop = map.getProperties();
        renderer = new OrthogonalTiledMapRenderer(map, 1/DoodleJump.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        player = new Doodle(world, this);

        mapWidth = prop.get("width", Integer.class).intValue() * prop.get("tilewidth", Integer.class).intValue();
        mapHeight = prop.get("height", Integer.class).intValue() * prop.get("tileheight", Integer.class).intValue();

        new B2WorldCreator(world, map);

        Gdx.input.setInputProcessor(this);

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
      /*  if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 4f), player.b2body.getWorldCenter(), true);
           // player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
            player.b2body.applyLinearImpulse(new Vector2(0.1f,4f),player.b2body.getWorldCenter(),true);
        }*/
    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f, 6, 2);
        player.update(dt);
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {

        update (delta);

        //clear the game screen with black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float cameraY = player.b2body.getPosition().y;
        if(cameraY < gamePort.getWorldHeight()/2)
            cameraY = gamePort.getWorldHeight() / 2;
        else if (cameraY > mapHeight - gamePort.getWorldHeight() / 2)
            cameraY = mapHeight - gamePort.getWorldHeight() / 2;

        gamecam.position.set(gamePort.getWorldWidth() /2, cameraY, 0);
        gamecam.update();
        renderer.setView(gamecam);

        //render game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
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
        if (screenX < DoodleJump.V_WIDTH/2)
            player.b2body.applyForceToCenter(-JUMP_WIDTH_MODIFIER, JUMP_HEIGHT_MODIFIER *-GRAVITY.y, true);
        if (screenX > DoodleJump.V_WIDTH/2)
            player.b2body.applyForceToCenter(JUMP_WIDTH_MODIFIER, JUMP_HEIGHT_MODIFIER *-GRAVITY.y, true);
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
}