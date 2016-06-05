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
import com.tq.doodle.Tools.FileHandler;


/**
 * Created by Inês on 02/06/2016.
 */
public class MenuScreen implements Screen {
    private DoodleJump game;

    //Variables of textures of menu
    private Texture background;
    private Texture title;
    private Texture menudoodle;
    private Stage stage;
    private TextureAtlas lvlMenuAtlas;
    private Skin skin;
    private FileHandler handler;

    private OrthographicCamera cam;
    private Viewport menuPort;

    //Variables of menu images
    public static int title_width = DoodleJump.V_WIDTH - 100;
    public static int title_height = 100;
    public static int doodle_width = 180;
    public static int doodle_height = 180;

    public static int btn_width = 150;
    public static int btn_height = 70;

    //Inicializaçao dos botoes
    private ImageButton playBtn;
    private ImageButton optionsBtn;
    private ImageButton scoresBtn;

    public MenuScreen(DoodleJump game) {
        this.game = game;
        this.background = new Texture("background.png");
        this.title = new Texture("title.png");
        menudoodle = new Texture("menu.png");

        cam = new OrthographicCamera();
        cam.setToOrtho(false);
        menuPort = new FitViewport(DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT,cam);
        handler = new FileHandler();


        initStage(game.batch);
    }

    public void handleInput(float dt) {

        if (playBtn.isPressed()) game.setScreen(new PlayScreen(game));
        if (optionsBtn.isPressed()){
            game.setScreen(new OptionsScreen(game));
            System.out.println("Ola");
            handler.write();
        }



    }


    public void update(float dt){
        handleInput(dt);
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
        game.batch.draw(title,DoodleJump.V_WIDTH / 2 - title_width /2, DoodleJump.V_HEIGHT / 2 + 200, title_width, title_height);
        game.batch.draw(menudoodle, DoodleJump.V_WIDTH/2 - doodle_width/2, DoodleJump.V_HEIGHT/2 - 50, doodle_width, doodle_height);
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        menuPort.update(width, height);
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
        this.stage = new Stage(menuPort, batch);

        lvlMenuAtlas = new TextureAtlas("Buttons.pack");
        skin = new Skin();
        skin.addRegions(lvlMenuAtlas);
        stage.clear();

        //PlayButton
        playBtn = new ImageButton(skin.getDrawable("play"));
        playBtn.setSize(170,75);
        playBtn.setPosition(DoodleJump.V_WIDTH /2 - playBtn.getWidth()/2,DoodleJump.V_HEIGHT /2 - 170);
        stage.addActor(playBtn);

        //OptionsButton
        optionsBtn = new ImageButton(skin.getDrawable("options"));
        optionsBtn.setSize(btn_width,btn_height);
        optionsBtn.setPosition(DoodleJump.V_WIDTH /2 - optionsBtn.getWidth()/2, DoodleJump.V_HEIGHT/2 - 250);
        stage.addActor(optionsBtn);

        //ScoresButton
        scoresBtn = new ImageButton(skin.getDrawable("scores"));
        scoresBtn.setSize(btn_width,btn_height);
        scoresBtn.setPosition(DoodleJump.V_WIDTH /2 - optionsBtn.getWidth()/2,DoodleJump.V_HEIGHT/ 2 - 330);
        stage.addActor(scoresBtn);

        Gdx.input.setInputProcessor(stage);
    }
}
