package com.tq.doodle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tq.doodle.DoodleJump;

/**
 * Created by Inês on 05/06/2016.
 */
public class GameOverScreen implements Screen{

    private DoodleJump game;
    private Texture background;
    private Texture lost;
    private Texture doodle;
    private Texture littledoodle;
    private Texture bottom;
    private Stage stage;
    private TextureAtlas lvlMenuAtlas;
    private Skin skin;
    private Viewport lostPort;
    private OrthographicCamera cam;
    private ImageButton playBtn;
    private ImageButton menuBtn;

    public static final int lost_width = 360;
    public static final int lost_height = 130;

    public GameOverScreen(DoodleJump game){
        this.game = game;
        this.background = new Texture("background.png");
        lost = new Texture("lost.png");
        doodle = new Texture("doodlelost.png");
        littledoodle = new Texture("doodlelostbaby.png");
        bottom = new Texture("bottomlost.png");

        cam = new OrthographicCamera();
        cam.setToOrtho(false);
        lostPort = new FitViewport(DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT,cam);
        initStage(game.batch);

    }


    public void update(float dt){
        handleInput(dt);
    }

    public void handleInput(float dt){

        if(playBtn.isPressed()) game.setScreen(new PlayScreen(game));
        if(menuBtn.isPressed()) game.setScreen(new MenuScreen(game));

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background,0, 0, DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT);
        game.batch.draw(lost, DoodleJump.V_WIDTH/2 - lost_width/2, DoodleJump.V_HEIGHT/2 + 180, lost_width,lost_height);
        game.batch.draw(doodle, DoodleJump.V_WIDTH/2+50, DoodleJump.V_HEIGHT/2 + 20);
        game.batch.draw(littledoodle, DoodleJump.V_WIDTH/2-170, DoodleJump.V_HEIGHT/2 -40, 85, 85);
        game.batch.draw(bottom, 0, 0);
        game.batch.end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        lostPort.update(width,height);
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
        game.dispose();
        background.dispose();
        lost.dispose();
        doodle.dispose();
        littledoodle.dispose();
        bottom.dispose();
        stage.dispose();
        lvlMenuAtlas.dispose();
        skin.dispose();
    }

    public void initStage(SpriteBatch batch) {
        this.stage = new Stage(lostPort, batch);

        lvlMenuAtlas = new TextureAtlas("Buttons.pack");
        skin = new Skin();
        skin.addRegions(lvlMenuAtlas);
        stage.clear();

        //PlayButton
        playBtn = new ImageButton(skin.getDrawable("play"));
        playBtn.setSize(170,75);
        playBtn.setPosition(DoodleJump.V_WIDTH /2 - playBtn.getWidth()/2,DoodleJump.V_HEIGHT /2 - 170);
        stage.addActor(playBtn);

        //MenuButtom
        menuBtn = new ImageButton(skin.getDrawable("menubtn"));
        menuBtn.setSize(170,75);
        menuBtn.setPosition(DoodleJump.V_WIDTH/2 - playBtn.getWidth()/2, DoodleJump.V_HEIGHT/2 - 270);
        stage.addActor(menuBtn);

        Gdx.input.setInputProcessor(stage);
    }
}
