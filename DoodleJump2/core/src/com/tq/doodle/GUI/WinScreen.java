package com.tq.doodle.GUI;

import com.badlogic.gdx.Gdx;
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

/**
 * Created by Inês on 05/06/2016.
 */
public class WinScreen implements Screen {
    private DoodleJump game;
    private PlayScreen screen;
    private Texture background;
    private Texture win;
    private Texture stars;
    private Texture doodle;
    private Texture jungle;
    private Stage stage;
    private TextureAtlas lvlMenuAtlas;
    private Skin skin;
    private Viewport winPort;
    private OrthographicCamera cam;
    private ImageButton menuBtn;
    private static Integer score;
    private static Label scoreLabel;
    private static Integer coins;
    private static Label coinLabel;
    private static Label result;

    public static final int win_width = 360;
    public static final int win_height = 170;


    public WinScreen(DoodleJump game, PlayScreen screen) {
        this.game = game;
        this.screen = screen;
        this.background = new Texture("background.png");
        win = new Texture("win.png");
        stars = new Texture("stars.png");
        doodle = new Texture("happydoodle.png");
        jungle = new Texture("jungle.png");
        cam = new OrthographicCamera();
        cam.setToOrtho(false);
        winPort = new FitViewport(DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT, cam);
        initStage(game.batch);
        game.highscores.add(screen.getResult());
        game.writeToFile();
    }

    public void update(float dt){
        handleInput(dt);
        screen.getCoin().update(dt);
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
        game.batch.draw(win, DoodleJump.V_WIDTH/2 - win_width/2, DoodleJump.V_HEIGHT/2 + 160, win_width,win_height);
        game.batch.draw(stars, DoodleJump.V_WIDTH/2 - win_width/2 - 20, DoodleJump.V_HEIGHT/2 + 240, 50, 40);
        game.batch.draw(doodle, DoodleJump.V_WIDTH/2 - 140, DoodleJump.V_HEIGHT/2 -25, 75, 75);
        game.batch.draw(jungle, 0, 0, DoodleJump.V_WIDTH, jungle.getHeight()-20);
        game.batch.draw(screen.getCoin().getFrame(),DoodleJump.V_WIDTH/2-50,DoodleJump.V_HEIGHT/2-15,30,30);
        game.batch.end();
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        winPort.update(width,height);
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
        this.stage = new Stage(winPort, batch);
        lvlMenuAtlas = new TextureAtlas("Buttons.pack");
        skin = new Skin();
        skin.addRegions(lvlMenuAtlas);
        stage.clear();

        //MenuButtom
        menuBtn = new ImageButton(skin.getDrawable("menubtn"));
        menuBtn.setSize(170, 75);
        menuBtn.setPosition(DoodleJump.V_WIDTH / 2 - 170 / 2, DoodleJump.V_HEIGHT / 2 - 270);
        stage.addActor(menuBtn);

        Table table = new Table();
        table.center();
        table.setFillParent(true);


        scoreLabel = new Label(String.format("%03d", screen.getFinalScore()), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel.setFontScale(2.5f);
        coinLabel = new Label(String.format("%02d x 20", screen.getFinalCoins()), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        coinLabel.setFontScale(2.5f);
        result = new Label(String.format("%04d", screen.getResult()), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        result.setFontScale(2.5f);

        table.add(scoreLabel).height(80).padLeft(110);
        table.row();
        table.add(coinLabel).height(80).padLeft(110);
        table.row();
        table.add(result).height(80).padLeft(110);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

}