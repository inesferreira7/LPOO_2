package com.tq.doodle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.physics.box2d.Body;
import com.tq.doodle.Screens.GameOverScreen;
import com.tq.doodle.Screens.MenuScreen;
import com.tq.doodle.Screens.PlayScreen;
import com.tq.doodle.Screens.WinScreen;
import com.tq.doodle.Sprites.Doodle;

import java.util.ArrayList;
import java.util.Collections;

public class DoodleJump extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 800;
	public static final float PPM = 100; //pixels per meter
	public static final short DOODLE_BIT = 2;
	public static final short WORLD_BIT = 4;
	public static final short GROUND_BIT = 1;
	public static final short COIN_BIT = 8;
	public boolean coinDraw;

    public int texture;
	public boolean music;
	public boolean sounds;
	public ArrayList<Integer> highscores;
	public Preferences scores;

    public Doodle doodle;


	@Override
	public void create () {
		batch = new SpriteBatch();
		scores = Gdx.app.getPreferences("My Scores");
		music = true;
		sounds = true;
        highscores = new ArrayList<Integer>();
        highscores.add(0);
        highscores.add(0);
        highscores.add(0);
        highscores.add(0);
        highscores.add(0);
        texture  = 0;


			String name = scores.getString("name","nogame");
			if(name.equals("nogame")){
				scores.putInteger("score1", 0);
				scores.putInteger("score2", 0);
				scores.putInteger("score3", 0);
				scores.putInteger("score4", 0);
				scores.putInteger("score5", 0);

		}
		else {
			readScores();
		}


		setScreen( new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public boolean getMusic(){
		return music;
	}

	public boolean getSounds(){return sounds;}

	public void readScores(){
		highscores.add(scores.getInteger("score1"));
		highscores.add(scores.getInteger("score2"));
		highscores.add(scores.getInteger("score3"));
		highscores.add(scores.getInteger("score4"));
		highscores.add(scores.getInteger("score5"));
	}

	public void writeToFile(){
		Collections.sort(highscores);
		Collections.reverse(highscores);

		scores.putString("name" ,"doodle dodge");
		scores.putInteger("score1",highscores.get(0));
		scores.putInteger("score2",highscores.get(1));
		scores.putInteger("score3",highscores.get(2));
		scores.putInteger("score4",highscores.get(3));
		scores.putInteger("score5",highscores.get(4));

		scores.flush();

	}

    public Doodle getDoodle(){
        return doodle;
    }

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }
}
