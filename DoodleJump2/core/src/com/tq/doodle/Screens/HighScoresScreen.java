package com.tq.doodle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tq.doodle.DoodleJump;

import java.util.Collections;

/**
 * Created by Inês on 06/06/2016.
 */
public class HighScoresScreen implements Screen {

    private DoodleJump game;
    private Texture background;
    private Stage stage;
    private TextureAtlas lvlMenuAtlas;
    private Skin skin;
    private Viewport scoresPort;
    private OrthographicCamera cam;
    private ImageButton menuBtn;

    //Table variables

    private static Integer score1;
    private static Label scoreLabel1;
    private static Integer score2;
    private static Label scoreLabel2;
    private static Integer score3;
    private static Label scoreLabel3;
    private static Integer score4;
    private static Label scoreLabel4;
    private static Integer score5;
    private static Label scoreLabel5;



    public HighScoresScreen(DoodleJump game){
        this.game = game;
        background = new Texture("newscoresscreen.png");

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
        //game.batch.draw(top, DoodleJump.V_WIDTH/2 - top_width/2 - 20, DoodleJump.V_HEIGHT/2 +180, top_width, top_height);
        //game.batch.draw(left, DoodleJump.V_WIDTH/2 +150, DoodleJump.V_HEIGHT/2 - 160, left.getWidth(), left.getHeight()*1.7f);
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
        menuBtn.setPosition(DoodleJump.V_WIDTH / 2 - 170 / 2, DoodleJump.V_HEIGHT / 2 - 290);
        stage.addActor(menuBtn);

        //Table implementation

        Table table = new Table();
        table.left();
        table.setFillParent(true);

        Collections.sort(game.highscores);
        Collections.reverse(game.highscores);

        scoreLabel1 = new Label(String.format("1.         %03d", game.highscores.get(0)), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel1.setFontScale(2.3f);
        scoreLabel2 = new Label(String.format("2.         %03d", game.highscores.get(1)), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel2.setFontScale(2.3f);
        scoreLabel3 = new Label(String.format("3.         %03d", game.highscores.get(2)), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel3.setFontScale(2.3f);
        scoreLabel4 = new Label(String.format("4.         %03d", game.highscores.get(3)), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel4.setFontScale(2.3f);
        scoreLabel5 = new Label(String.format("5.         %03d", game.highscores.get(4)), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel5.setFontScale(2.3f);

        table.add(scoreLabel1).height(65).padLeft(150);
        table.row();
        table.add(scoreLabel2).height(65).padLeft(150);
        table.row();
        table.add(scoreLabel3).height(65).padLeft(150);
        table.row();
        table.add(scoreLabel4).height(65).padLeft(150);
        table.row();
        table.add(scoreLabel5).height(65).padLeft(150);
        table.row();
        stage.addActor(table);




        Gdx.input.setInputProcessor(stage);


    }


}
