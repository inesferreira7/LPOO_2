package com.tq.doodle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;;
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

/**
 * Created by Inês on 01/06/2016.
 */
public class PlayScreen implements Screen {

    private DoodleJump game;
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


    public PlayScreen(DoodleJump game){
        this.game= game;

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
        player = new Doodle(world);

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        mapWidth = prop.get("width", Integer.class).intValue() * prop.get("tilewidth", Integer.class).intValue();
        mapHeight = prop.get("height", Integer.class).intValue() * prop.get("tileheight", Integer.class).intValue();

        //Definir as plataformas como um corpo
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/DoodleJump.PPM, (rect.getY() + rect.getHeight()/2)/DoodleJump.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2 /DoodleJump.PPM, rect.getHeight()/2 /DoodleJump.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Definir o chao como um corpo
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/DoodleJump.PPM, (rect.getY() + rect.getHeight()/2)/DoodleJump.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2 /DoodleJump.PPM, rect.getHeight()/2 /DoodleJump.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if (Gdx.input.justTouched())
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f, 6, 2);
        //gamecam.position.y = player.b2body.getPosition().y;


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

    }
}
