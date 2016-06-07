package com.tq.doodle.Logic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.GUI.PlayScreen;
import java.util.Random;

/**
 * Created by Inês on 04/06/2016.
 */
public class Coin extends Sprite {
    private Array<TextureRegion> frames;
    private Rectangle coinRectangle;
    private Array<Rectangle> coinRectangles;
    private static final int XVAR = DoodleJump.V_WIDTH;
    private Random randX;
    private static int currentHeight;
    public World world;
    float maxFrameTime;
    float currFrameTime;
    int frameCount;
    int frame;
    double gap;
    float xPos;


    /**
     * @brief Coin constructor with the necessary parameters. Contains the cicle for the random coin generator where
     * a gap is incremented untill it is higher or equal to the level height - 600.
     * @param region TextureRegion for the coin animation.
     * @param frameCount Frames for the animation.
     * @param time Time for the animation.
     * @param screen Play screen where the coin will appear.
     * @param world Game world.
     */
    public Coin(TextureRegion region, int frameCount, float time, PlayScreen screen, World world) {
        this.world = world;
        coinRectangles = new Array<Rectangle>();
        coinRectangle = new Rectangle();
        currentHeight = (int)(6000/DoodleJump.PPM);
        while (screen.getMapHeight() -600>= currentHeight) {
            gap = 370;
            currentHeight += gap;
            defineCoin(region, frameCount, time);
            createCoin(currentHeight);
        }
    }

    /**
     * @brief Creates the necessary elements for the coin animation.
     * @param region TextureRegion for the coin animation.
     * @param frameCount Frames for the animation.
     * @param time Time for the animation.
     */
    public void defineCoin(TextureRegion region, int frameCount, float time) {
        frames = new Array<TextureRegion>();
        TextureRegion temp;
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < 6; i++) {
            temp = new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight());
            frames.add(temp);
        }
        this.frameCount = frameCount;
        maxFrameTime = time / frameCount;
    }

    /**
     * @brief Creates the new coin with a random X position, the given Y, adds a rectangle around it and pushes the rectangle
     * to the rectangles array.
     * @param currentHeight Current height where the new coin will be placed.
     */
    public void createCoin(double currentHeight){
        randX = new Random();
        xPos = randX.nextInt((XVAR -80)+80 );
        if (xPos <= 40) xPos+=50;
        coinRectangle = new Rectangle(xPos / DoodleJump.PPM , (int) currentHeight / DoodleJump.PPM,30/DoodleJump.PPM,30/DoodleJump.PPM);
        coinRectangles.add(coinRectangle);
    }

    /**
     * @brief Updates the timer for the coin animation.
     * @param dt Delta time for the coin animation.
     */
    public void update(float dt) {
        currFrameTime += dt;
        if (currFrameTime > maxFrameTime) {
            frame++;
            if (frame == 6) frame = 1;
            currFrameTime = 0;
        }
    }

    /**
     * @brief Returns the coin animation.
     * @return Returns the coin animation.
     */
    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    /**
     * @brief Disposes the coin animation to free up space.
     */
   public void dispose() {
        for (TextureRegion frame : frames) {
            frame.getTexture().dispose();
        }
    }

    /**
     * @brief Draws the coin in the screen in the given position.
      * @param sb SpriteBatch necessary to draw the coin.
     */
   public void render(SpriteBatch sb) {

        for(int i = 0; i < coinRectangles.size; i++){
            sb.draw(getFrame(),coinRectangles.get(i).getX()-15/DoodleJump.PPM,coinRectangles.get(i).getY() -15/DoodleJump.PPM,30 / DoodleJump.PPM, 30 / DoodleJump.PPM );
        }

   }

    /**
     * @brief Returns the rectangles array.
     * @return Returns the rectangles array.
     */
    public Array<Rectangle> getCoinRectangles() {
        return coinRectangles;
    }
}