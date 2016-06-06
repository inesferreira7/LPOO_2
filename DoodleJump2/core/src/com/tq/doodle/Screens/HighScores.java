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
 * Created by Inês on 06/06/2016.
 */
public class HighScores  implements Screen {

    private DoodleJump game;
    private Texture background;
    private Texture title;
    private Texture top;
    private Stage stage;
    private TextureAtlas lvlMenuAtlas;
    private Skin skin;
    private Viewport scoresPort;
    private OrthographicCamera cam;
    private ImageButton menuBtn;

    public static final int top_width = 430;
    public static final int top_height = 165;


    public HighScores(DoodleJump game){
        this.game = game;
        background = new Texture("background.png");
        top = new Texture("high-scores-top.png");

        cam = new OrthographicCamera();
        cam.setToOrtho(false);
        scoresPort = new FitViewport(DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT,cam);

        initStage(game.batch);
    }

    public void update(float dt)
    {
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
        game.batch.draw(background,0, 0, DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT);
        game.batch.draw(top, DoodleJump.V_WIDTH/2 - top_width/2 - 20, DoodleJump.V_HEIGHT/2 +170, top_width, top_height);
        game.batch.end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        scoresPort.update(width,height);
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
        stage.dispose();
    }

    public void initStage(SpriteBatch batch) {
        this.stage = new Stage(scoresPort, batch);
        lvlMenuAtlas = new TextureAtlas("Buttons.pack");
        skin = new Skin();
        skin.addRegions(lvlMenuAtlas);
        stage.clear();

        //MenuButtom
        menuBtn = new ImageButton(skin.getDrawable("menubtn"));
        menuBtn.setSize(170, 75);
        menuBtn.setPosition(DoodleJump.V_WIDTH / 2 - 170 / 2, DoodleJump.V_HEIGHT / 2 - 270);
        stage.addActor(menuBtn);

        Gdx.input.setInputProcessor(stage);

    }
}
