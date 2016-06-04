package com.tq.doodle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tq.doodle.DoodleJump;

/**
 * Created by Inês on 02/06/2016.
 */
public class PauseScreen implements Screen {

    private DoodleJump game;
    private Texture background_pause;
    private Stage stage;
    private TextureAtlas lvlMenuAtlas;
    private Skin skin;
    private Viewport pausePort;
    private OrthographicCamera cam;
    private ImageButton menuBtn;
    private ImageButton resumeBtn;


    public static final int btn_width = 140;
    public static final int btn_height = 60;

    public PauseScreen (DoodleJump game){
        this.game = game;
        this.background_pause = new Texture("background_pause.png");
        cam = new OrthographicCamera();
        cam.setToOrtho(false);
        pausePort = new FitViewport(DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT, cam);

        initStage(game.batch);
    }

    public void update(float dt){
        handleInput(dt);
    }

    public void handleInput(float dt){

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
        game.batch.draw(background_pause, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        pausePort.update(width,height);
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
        background_pause.dispose();
    }


    public void initStage(SpriteBatch batch) {
        this.stage = new Stage(pausePort, batch);

        lvlMenuAtlas = new TextureAtlas("Buttons.pack");
        skin = new Skin();
        skin.addRegions(lvlMenuAtlas);
        stage.clear();

        //ResumeButton
        resumeBtn = new ImageButton(skin.getDrawable("resume"));
        resumeBtn.setSize(btn_width,btn_height);
        resumeBtn.setPosition(DoodleJump.V_WIDTH/2 - btn_width/2, DoodleJump.V_HEIGHT/2 - 230);
        stage.addActor(resumeBtn);

        //OptionsButton
        menuBtn = new ImageButton(skin.getDrawable("menubtn"));
        menuBtn.setSize(btn_width,btn_height);
        menuBtn.setPosition(DoodleJump.V_WIDTH/2 - btn_width/2, DoodleJump.V_HEIGHT/2 -330 );
        stage.addActor(menuBtn);

        Gdx.input.setInputProcessor(stage);
    }
}
