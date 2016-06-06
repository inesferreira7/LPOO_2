package com.tq.doodle.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tq.doodle.DoodleJump;

/**
 * Created by Inês on 01/06/2016.
 */
public class Hud implements Disposable{

    public Stage stage;
    private Viewport viewport;
    private float timeCount;

    private Image pause;
    private Texture pauseTexture;
    private static Integer score;
    private static Integer coins;
    private Label pauseLabel;
    private static Label scoreLabel;
    private static Label coinsLabel;


    public Hud(SpriteBatch sb) {
        score = 500;
        coins = 0;
        viewport = new FitViewport(DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        pauseTexture = new Texture("pause.png");
        pause = new Image(pauseTexture);
        scoreLabel = new Label(String.format("%04d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel.setFontScale(2);
        coinsLabel = new Label(String.format("%03d", coins), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        coinsLabel.setFontScale(2);

        table.add(pause).expandX().padTop(18); //pause button alligned with the others
        table.add(scoreLabel).expandX().padTop(10);
        table.add(coinsLabel).expandX().padTop(10);

        stage.addActor(table);
    }

        public static void addScore(int value){
            if(score == 0){
                score = 0;
                scoreLabel.setText(String.format("%03d",score));
            }
            else {
                score += value;
                scoreLabel.setText(String.format("%03d", score));
            }

    }

    public static void addCoin(){
        coins+=1;
        coinsLabel.setText(String.format("%03d", coins));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if(score <= 0){
                score = 0;
            }
            else {
                score--;
            }
            scoreLabel.setText(String.format("%04d", score));
            timeCount = 0.8f;
        }
    }

    public int getScore(){
        return score;
    }
}

