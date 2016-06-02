package com.tq.doodle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.tq.doodle.DoodleJump;

/**
 * Created by Inês on 02/06/2016.
 */
public class PauseScreen implements Screen {

    private DoodleJump game;
    private Texture background_pause;

    public PauseScreen (DoodleJump game){
        this.game = game;
        this.background_pause = new Texture("background_pause.png");
    }

    public void update(float dt){

        handleInput(dt);
    }

    public void handleInput(float dt){

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
    }
}
