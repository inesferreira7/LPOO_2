package com.tq.doodle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.tq.doodle.DoodleJump;


/**
 * Created by Inês on 02/06/2016.
 */
public class MenuScreen implements Screen {
    private DoodleJump game;

    //Variables of textures of menu
    private Texture background;
    private Texture title;
    private Texture playBtn;
    private Texture optionsBtn;
    private Texture scoresBtn;
    private Texture menudoodle;
    private Texture ovni;
    private OrthographicCamera menucam;

    public static int title_width = Gdx.graphics.getWidth() - 100;
    public static int title_height = 140;

    public static int btn_width = 150;
    public static int btn_height = 70;

    public static int doodle_width = 200;
    public static int doodle_height = 200;

    public static int play_width = 170;
    public static int play_height = 75;

    public MenuScreen(DoodleJump game) {
        this.game = game;
        this.background = new Texture("background.png");
        this.title = new Texture("title.png");
        this.optionsBtn = new Texture("options.png");
        this.scoresBtn = new Texture("scores.png");
        menudoodle = new Texture("menu.png");
        menucam = new OrthographicCamera();

        //Buttonplay variables
        this.playBtn = new Texture("play.png");

    }

    public void handleInput(float dt) {

        if (Gdx.input.isTouched()) {
            int cursor_x = Gdx.input.getX();
            int cursor_y = Gdx.input.getY();
            if (cursor_x > Gdx.graphics.getWidth() / 2 - play_width / 2 && cursor_x < Gdx.graphics.getWidth() / 2 + play_width / 2) {
                if (cursor_y > Gdx.graphics.getHeight() / 2 + 195 && cursor_y < Gdx.graphics.getHeight() / 2 + 195 + play_height) {
                    game.setScreen(new PlayScreen(game));
                }
            }
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
        game.batch.draw(background,0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(title,Gdx.graphics.getWidth() / 2 - title_width /2, Gdx.graphics.getHeight() / 2 + 200, title_width, title_height);
        game.batch.draw(playBtn,Gdx.graphics.getWidth() /2 - play_width/2,Gdx.graphics.getHeight() /2 - 250, play_width, play_height);
        game.batch.draw(optionsBtn, Gdx.graphics.getWidth()/4 - btn_width/4, Gdx.graphics.getHeight()/2 - 350, btn_width, btn_height);
        game.batch.draw(scoresBtn, Gdx.graphics.getWidth()/2 + optionsBtn.getWidth()/2 -10, Gdx.graphics.getHeight() / 2 - 350, btn_width, btn_height);
        game.batch.draw(menudoodle, Gdx.graphics.getWidth()/2 - doodle_width/2, Gdx.graphics.getHeight()/2 - 100, doodle_width, doodle_height);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        background.dispose();
        title.dispose();
        playBtn.dispose();
        scoresBtn.dispose();
        optionsBtn.dispose();
        menudoodle.dispose();

    }
}
