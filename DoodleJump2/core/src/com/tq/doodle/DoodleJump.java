package com.tq.doodle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tq.doodle.Screens.GameOverScreen;
import com.tq.doodle.Screens.MenuScreen;
import com.tq.doodle.Screens.PlayScreen;

public class DoodleJump extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 800;
	public static final float PPM = 100; //pixels per meter
	public boolean music;
	public boolean sounds;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen( new MenuScreen(this));
		music = true;
		sounds = true;
	}

	@Override
	public void render () {
		super.render();
	}

	public boolean getMusic(){
		return music;
	}

	public boolean getSounds(){return sounds;}
}
