package com.tq.doodle.Logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.GUI.PlayScreen;
import java.util.Random;

/**
 * Created by Utilizador on 02/06/2016.
 */
public class Platform extends Sprite {
    private static final int XVAR = DoodleJump.V_WIDTH - 40 ;
    private static final int YVAR = DoodleJump.V_HEIGHT;

    public World world;
    public Body platBody;
    private Array<Body> platforms;
    private Texture newPlat;
    public Rectangle bounds;
    private Array<Rectangle> rectangles;
    private Random randX;
    private Random randY;
    private Random randGap;
    private static int currentHeight = 0;
    private double gap = 0;
    private int rand;
    private float xPos;


    //private Texture teste;

    /**
     * @brief Platforms constructor with the necessary parameters. Generates all the platforms for the level with a gap of 120, 180 or 240 pixels ( depending on the random number
     * generator). Increases the current height where the platform will be placed with the gap value, untill the current height is higher than the map height - 300.
     * @param world Game world.
     * @param screen Play screen where the platforms will appear.
     */
    public Platform(World world, PlayScreen screen){
        this.world = world;
        newPlat = new Texture("plat.png");
        platforms = new Array<Body>();
        rectangles = new Array<Rectangle>();
        bounds = new Rectangle();
        randY =  new Random();
        currentHeight = (int)(10000/DoodleJump.PPM);

        while(screen.getMapHeight() - 300 >= currentHeight ){
            //randGap = new Random();
            //rand = randY.nextInt(2);
            //if(rand == 0) gap = 100;
            //if(rand == 1) gap = 300;
            //if(rand == ) gap = 240;
            currentHeight+=200;
            definePlatform(currentHeight);
        }
    }

    /**
     * @brief Creates the platform with the Y position given by the current height and a random X value. Creates the body and the rectangle around the platform necessary
     * for the collision and adds them to the respective array.
     * @param currentHeight The Y position where the platform will be placed.
     */
    public void definePlatform(double currentHeight){
        randX = new Random();
        bounds =  new Rectangle();

        xPos = randX.nextInt((XVAR -80)+80 );
        if (xPos <= 40) xPos+=50;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32/ DoodleJump.PPM,8/DoodleJump.PPM);

        BodyDef bdef = new BodyDef();

        bdef.position.set(xPos / DoodleJump.PPM, (int)currentHeight/DoodleJump.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        platBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        shape.setAsBox(32/ DoodleJump.PPM,8/DoodleJump.PPM);

        fdef.shape = shape;

        platBody.createFixture(fdef);
        bounds.set(platBody.getPosition().x-26/DoodleJump.PPM,platBody.getPosition().y+3/DoodleJump.PPM,54/DoodleJump.PPM, 0);
        rectangles.add(bounds);
        platforms.add(platBody);
    }

    /**
     * @brief Draws all the platform in their respective X and Y positions.
     * @param sb Spritebatch necessary to draw the platforms.
     */
    public void render(SpriteBatch sb){

        for(int i = 0; i < platforms.size  ; i++){
            sb.draw(newPlat,platforms.get(i).getPosition().x-44/DoodleJump.PPM,platforms.get(i).getPosition().y-23/DoodleJump.PPM,84/DoodleJump.PPM,30/DoodleJump.PPM);
        }

    }

    /**
     * @brief Returns the array of the rectangles of all the platforms.
     * @return Returns the array of the rectangles of all the platforms.
     */
    public Array<Rectangle> getRectangles() {
        return rectangles;
    }

    /**
     * @brief Returns the array of bodies around all of the platforms.
     * @return Returns the array of bodies around all of the platforms.
     */
    public Array<Body> getPlatforms() {
        return platforms;
    }

    /**
     * @brief Disposes the platform texture to free up space.
     */
    public void dispose(){
        newPlat.dispose();
    }
}
