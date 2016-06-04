package com.tq.doodle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.Sprites.Doodle;

/**
 * Created by Inês on 04/06/2016.
 */
public class OptionsScreen implements Screen{

    private DoodleJump game;
    private Texture background;
    private Stage stage;
    private TextureAtlas lvlMenuAtlas;
    private Skin skin;
    private Viewport optionsPort;
    private OrthographicCamera cam;
    private ImageButton soundBtn;
    private ImageButton menuBtn;
    private boolean musicon;
    private int touchs;

    public OptionsScreen(DoodleJump game){
        this.game = game;
        this.background = new Texture("background.png");
        cam = new OrthographicCamera();
        cam.setToOrtho(false);
        optionsPort = new FitViewport(DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT,cam);
        musicon = true;

        initStage(game.batch);
    }

    public void update(float dt){
        handleInput(dt);
    }

    public void handleInput(float dt){

            if (touchs > 0){
                if (game.music == true) game.music = false;
                else if (game.music == false) game.music = true;
            }

        touchs =0;


        if(menuBtn.isPressed()){
            game.setScreen(new MenuScreen(game));
        }
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
        game.batch.draw(background,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        optionsPort.update(width,height);

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

    public void initStage(SpriteBatch batch) {
        this.stage = new Stage(optionsPort, batch);

        lvlMenuAtlas = new TextureAtlas("Buttons.pack");
        skin = new Skin();
        skin.addRegions(lvlMenuAtlas);
        stage.clear();

        //Soundbutton
        if(game.music == true) soundBtn = new ImageButton(skin.getDrawable("soundon"), skin.getDrawable("soundoff"), skin.getDrawable("soundoff"));
        if(game.music == false) soundBtn = new ImageButton(skin.getDrawable("soundoff"), skin.getDrawable("soundon"), skin.getDrawable("soundon"));
        soundBtn.setSize(soundBtn.getWidth(),soundBtn.getHeight());
        soundBtn.setPosition(DoodleJump.V_WIDTH/2 - soundBtn.getWidth()/2, DoodleJump.V_HEIGHT/2);
        stage.addActor(soundBtn);

        soundBtn.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
               touchs++;
            }
        });

        //Back to Menu Button
        menuBtn = new ImageButton(skin.getDrawable("menubtn"));
        menuBtn.setSize(170, 80);
        menuBtn.setPosition(DoodleJump.V_WIDTH/2 - menuBtn.getWidth() /2, DoodleJump.V_HEIGHT/2 -200);
        stage.addActor(menuBtn);

        Gdx.input.setInputProcessor(stage);

    }
}
