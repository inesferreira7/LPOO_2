package com.tq.doodle.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tq.doodle.DoodleJump;

/**
 * Created by Inês on 04/06/2016.
 */
public class OptionsScreen implements Screen {

    private DoodleJump game;
    private Texture background;
    private Texture ovni;
    private Stage stage;
    private TextureAtlas lvlMenuAtlas;
    private TextureAtlas lvlDoodle;
    private Skin skin;
    private Skin skinDoodle;
    private Viewport optionsPort;
    private OrthographicCamera cam;
    private boolean musicon;
    private int touchs;

    private ImageButton soundBtn;
    private ImageButton menuBtn;
    private ImageButton bunnyDoodle;
    private ImageButton ghostDoodle;
    private ImageButton iceDoodle;
    private ImageButton jungleDoodle;
    private ImageButton spaceDoodle;
    private ImageButton waterDoodle;

    public static final int title_width = 420;
    public static final int title_height = 250;

    public OptionsScreen(DoodleJump game) {
        this.game = game;
        this.background = new Texture("newoptionsscreen.png");
        ovni = new Texture("ovni.png");
        cam = new OrthographicCamera();
        cam.setToOrtho(false);
        optionsPort = new FitViewport(DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT, cam);
        musicon = true;

        initStage(game.batch);
    }

    public void update(float dt) {
        handleInput(dt);
    }

    public void handleInput(float dt) {

        if (touchs > 0){
            if (game.music == true) game.music = false;
            else if (game.music == false) game.music = true;
        }
        touchs =0;


        if (menuBtn.isPressed())game.setScreen(new MenuScreen(game));
        if (bunnyDoodle.isPressed()) game.setTexture(1);
        if (jungleDoodle.isPressed()) game.setTexture(2);
        if (ghostDoodle.isPressed()) game.setTexture(3);
        if (iceDoodle.isPressed()) game.setTexture(4);
        if (spaceDoodle.isPressed()) game.setTexture(5);
        if (waterDoodle.isPressed()) game.setTexture(6);

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
        game.batch.draw(background, 0, 0, DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT);
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        optionsPort.update(width, height);

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
        ovni.dispose();
        stage.dispose();
        lvlMenuAtlas.dispose();
        skin.dispose();
    }

    public void initStage(SpriteBatch batch) {
        this.stage = new Stage(optionsPort, batch);

        lvlMenuAtlas = new TextureAtlas("Buttons.pack");
        skin = new Skin();
        skin.addRegions(lvlMenuAtlas);
        stage.clear();

        lvlDoodle = new TextureAtlas("Doodles.pack");
        skinDoodle = new Skin();
        skin.addRegions(lvlDoodle);
        stage.clear();



        //Soundbutton
        if (game.music == true)
            soundBtn = new ImageButton(skin.getDrawable("soundon"), skin.getDrawable("soundoff"), skin.getDrawable("soundoff"));
        if (game.music == false)
            soundBtn = new ImageButton(skin.getDrawable("soundoff"), skin.getDrawable("soundon"), skin.getDrawable("soundon"));
        soundBtn.setSize(80, 80);
        soundBtn.setPosition(DoodleJump.V_WIDTH / 2 - soundBtn.getWidth() / 2, DoodleJump.V_HEIGHT / 2 + 70);
        stage.addActor(soundBtn);

        soundBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                touchs++;
            }
        });

        //Back to Menu Button
        menuBtn = new ImageButton(skin.getDrawable("menubtn"));
        menuBtn.setSize(170, 80);
        menuBtn.setPosition(DoodleJump.V_WIDTH / 2 - menuBtn.getWidth() / 2, DoodleJump.V_HEIGHT / 2 - 350);
        stage.addActor(menuBtn);


        //Escolher skin do doodle;

        bunnyDoodle = new ImageButton(skin.getDrawable("bunny"));
        bunnyDoodle.setSize(170, 80);
        bunnyDoodle.setPosition(DoodleJump.V_WIDTH / 2 - menuBtn.getWidth() / 2 - 130, DoodleJump.V_HEIGHT / 2 - 70);
        stage.addActor(bunnyDoodle);

        ghostDoodle = new ImageButton(skin.getDrawable("ghost"));
        ghostDoodle.setSize(170, 80);
        ghostDoodle.setPosition(DoodleJump.V_WIDTH / 2 - menuBtn.getWidth() / 2, DoodleJump.V_HEIGHT / 2 - 70);
        stage.addActor(ghostDoodle);

        iceDoodle = new ImageButton(skin.getDrawable("ice"));
        iceDoodle.setSize(170, 80);
        iceDoodle.setPosition(DoodleJump.V_WIDTH / 2 - menuBtn.getWidth() / 2 + 130, DoodleJump.V_HEIGHT / 2 - 70);
        stage.addActor(iceDoodle);

        jungleDoodle = new ImageButton(skin.getDrawable("jungle"));
        jungleDoodle.setSize(170, 80);
        jungleDoodle.setPosition(DoodleJump.V_WIDTH / 2 - menuBtn.getWidth() / 2 - 130, DoodleJump.V_HEIGHT / 2 - 200);
        stage.addActor(jungleDoodle);

        spaceDoodle = new ImageButton(skin.getDrawable("space"));
        spaceDoodle.setSize(170, 80);
        spaceDoodle.setPosition(DoodleJump.V_WIDTH / 2 - menuBtn.getWidth() / 2, DoodleJump.V_HEIGHT / 2 - 200);
        stage.addActor(spaceDoodle);

        waterDoodle = new ImageButton(skin.getDrawable("underwater"));
        waterDoodle.setSize(170, 80);
        waterDoodle.setPosition(DoodleJump.V_WIDTH / 2 - menuBtn.getWidth() / 2 + 130, DoodleJump.V_HEIGHT / 2 - 200);
        stage.addActor(waterDoodle);


        Gdx.input.setInputProcessor(stage);

    }
}
