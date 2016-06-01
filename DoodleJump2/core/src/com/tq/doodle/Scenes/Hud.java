package com.tq.doodle.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tq.doodle.DoodleJump;

/**
 * Created by Inês on 01/06/2016.
 */
public class Hud {

    public Stage stage;
    private Viewport viewport;

    private Integer lives;
    private Integer score;
    private Integer coins;
    Label livesLabel;
    Label scoreLabel;
    Label coinsLabel;

    public Hud(SpriteBatch sb){
        lives = 3;
        score = 0;
        coins = 0;
        viewport = new FitViewport(DoodleJump.V_WIDTH, DoodleJump.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        livesLabel = new Label(String.format("%d", lives), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        livesLabel.setFontScale(2);
        scoreLabel = new Label(String.format("%04d", score), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        scoreLabel.setFontScale(2);
        coinsLabel = new Label(String.format("%03d", coins), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        coinsLabel.setFontScale(2);


        table.add(livesLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);
        table.add(coinsLabel).expandX().padTop(10);

        stage.addActor(table);

    }

}
