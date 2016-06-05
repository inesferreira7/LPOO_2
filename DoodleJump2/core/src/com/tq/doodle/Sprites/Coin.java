package com.tq.doodle.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.Screens.PlayScreen;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Inês on 04/06/2016.
 */
public class Coin extends Sprite {

    private Array<TextureRegion> frames;
    private ArrayList<Body> coins;
    private Array<Coin> coinList;
    public World world;
    float maxFrameTime;
    float currFrameTime;
    int frameCount;
    int frame;
    private Random randX;
    private Random randY;
    private Random randGap;
    private static int currentHeight = 0;
    private Body coinBody;
    private static final int XVAR = DoodleJump.V_WIDTH;
    private static final int YVAR = DoodleJump.V_HEIGHT;

    public boolean drawCoin = true;



    public Coin(TextureRegion region, int frameCount, float time, PlayScreen screen, World world) {
        this.world = world;
        coins = new ArrayList<Body>();
        coinList = new Array();

        currentHeight = 0;
        while (screen.getMapHeight() >= currentHeight) {
            randGap = new Random();
            double gap;

            //gap = randGap.nextInt(1600-600) + 600;
            gap = randGap.nextInt(300-150)+100;
            currentHeight += gap;
            defineCoin(region, frameCount, time, currentHeight);
        }



    }

    public void defineCoin(TextureRegion region, int frameCount, float time, double currentHeight) {
        randX = new Random();
        randY = new Random();
        frames = new Array<TextureRegion>();
        TextureRegion temp;
        int frameWidth = region.getRegionWidth() / frameCount;

        for (int i = 0; i < 6; i++) {
            temp = new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight());
            frames.add(temp);
        }

        this.frameCount = frameCount;
        maxFrameTime = time / frameCount;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15 / DoodleJump.PPM, 15 / DoodleJump.PPM);

        BodyDef bdef = new BodyDef();
        bdef.position.set(randX.nextInt(XVAR) / DoodleJump.PPM, (int) currentHeight / DoodleJump.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        coinBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = DoodleJump.COIN_BIT;
        coinBody.createFixture(fdef).setUserData(this);
        coinBody.createFixture(fdef);
        coins.add(coinBody);
    }


    public void update(float dt) {
        currFrameTime += dt;

        if (currFrameTime > maxFrameTime) {
            frame++;
            if (frame == 6) frame = 1;
            currFrameTime = 0;
        }

    }

    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    public void dispose() {
        for (TextureRegion frame : frames) {
            frame.getTexture().dispose();
        }
    }

    public void render(SpriteBatch sb) {

        for (int i = 0; i < coins.size() - 2; i++) {
            sb.draw(getFrame(), coins.get(i).getPosition().x - 15 / DoodleJump.PPM, coins.get(i).getPosition().y - 15 / DoodleJump.PPM, 30 / DoodleJump.PPM, 30 / DoodleJump.PPM);
        }
    }

    public ArrayList<Body> getCoins() {
        return coins;
    }


}