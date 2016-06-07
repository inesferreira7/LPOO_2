package com.tq.doodle.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.Screens.PlayScreen;



/**
 * Created by Inês on 01/06/2016.
 */
public class Doodle extends Sprite{
    public final static Vector2 GRAVITY = new Vector2(0, -10);
    private final static int JUMP_HEIGHT_MODIFIER = 30;
    private final static int JUMP_WIDTH_MODIFIER = 120;

    public World world;
    public PlayScreen screen;
    public Body b2body;
    public boolean flipFlag;
    private boolean win ;

    private int texture;
    private Texture doodleNormal;
    private Texture doodleBunny;
    private Texture doodleJungle;
    private Texture doodleGhost;
    private Texture doodleIce;
    private Texture doodleSpace;
    private Texture doodleWater;
    private TextureRegion normalDoodle;
    public Rectangle doodleBounds;

    /**
     * @brief Doodle constructor with the necessary parameters. Initializes all the different textures of the doodle.
     * @param world Game world.
     * @param screen Play screen where the doodle will appear.
     */
    public Doodle(World world, PlayScreen screen){
        flipFlag = false;
        this.world = world;
        this.screen=screen;
        doodleBounds = new Rectangle();
        doodleNormal = new Texture("Jump.png");
        doodleBunny = new Texture("bunny.png");
        doodleJungle = new Texture("jungledoodle.png");
        doodleGhost = new Texture("ghost.png");
        doodleIce = new Texture("ice.png");
        doodleSpace = new Texture("space.png");
        doodleWater = new Texture("underwater.png");
        chooseDoodle();
        defineDoodle();
        win = false;
        texture =0;
    }

    /**
     * @brief Updates the doodle position during the course of the game and verifies if the doodle hit the screen borders.
     * @param dt Delta time for the doodle.
     */
    public void update(float dt){

        setPosition(b2body.getPosition().x - 32/DoodleJump.PPM, b2body.getPosition().y - 32/DoodleJump.PPM);

        if(b2body.getPosition().x <=0.35){
            b2body.setTransform(0.35f,b2body.getPosition().y,0);
        }
        if(b2body.getPosition().x >= DoodleJump.V_WIDTH/DoodleJump.PPM - 0.3f){
            b2body.setTransform(DoodleJump.V_WIDTH/DoodleJump.PPM -0.3f ,b2body.getPosition().y,0);
        }

        doodleBounds.setPosition(b2body.getPosition().x-32/DoodleJump.PPM,b2body.getPosition().y-32/DoodleJump.PPM);
    }

    /**
     * @brief Creates the doodle body with the desired shape and size and a rectangle around it.
     */
    public void defineDoodle(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(100/DoodleJump.PPM, 30/ DoodleJump.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(24/DoodleJump.PPM,16/DoodleJump.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = DoodleJump.DOODLE_BIT;
        fdef.filter.maskBits = DoodleJump.GROUND_BIT | DoodleJump.WORLD_BIT | DoodleJump.COIN_BIT;
        b2body.createFixture(fdef).setUserData(this);
        doodleBounds.set(b2body.getPosition().x-32/DoodleJump.PPM, b2body.getPosition().y-32/DoodleJump.PPM,64/DoodleJump.PPM, 64/DoodleJump.PPM);
    }

    /**
     * @brief Changes the doodle texture depending on the skin that was selected in the option screen.
     */
    public void chooseDoodle(){
        switch(screen.getGame().getTexture()){
            case 0:
                normalDoodle = new TextureRegion(doodleNormal,0,0,64,64);
                break;
            case 1:
                normalDoodle = new TextureRegion(doodleBunny,0,0,64,64);
                break;
            case 2:
                normalDoodle = new TextureRegion(doodleJungle,0,0,64,64);
                break;
            case 3:
                normalDoodle = new TextureRegion(doodleGhost,0,0,64,64);
                break;
            case 4:
                normalDoodle = new TextureRegion(doodleIce,0,0,64,64);
                break;
            case 5:
                normalDoodle = new TextureRegion(doodleSpace,0,0,64,64);
                break;
            case 6:
                normalDoodle = new TextureRegion(doodleWater,0,0,64,64);
                break;
            default:
                break;
        }

    }

    /**
     * @brief Flips the doodle image depending if the doodle is goin right or left.
     * @param screenX X position where the screen is being touched.
     */
    public void flipDoodle(boolean turned, int screenX) {
            if (flipFlag) {
                if(screenX >  DoodleJump.V_WIDTH/2){
                    normalDoodle.flip(true, false);
                    flipFlag = false;
                }
                if(screenX <  DoodleJump.V_WIDTH/2){
                    normalDoodle.flip(false, false);
                    flipFlag = true;

                }
            }
            if (!flipFlag) {
                if(screenX >  DoodleJump.V_WIDTH/2){
                    normalDoodle.flip(false, false);
                }
                if(screenX < DoodleJump.V_WIDTH/2){
                    turned = true;
                    flipFlag = true;
                    normalDoodle.flip(true, false);

                }
            }
        }

    /**
     * @brief Returns the rectangle around the doodle.
     * @return Returns the rectangle around the doodle.
     */
    public Rectangle getDoodleBounds() {
        return doodleBounds;
    }

    /**
     * @brief Verifies if the doodle is colliding with a platform.
     * @param platform Platform rectangle to verify the collision.
     * @return Returns a true boolean if the doodle is colliding with a platform.
     */
    public boolean collides(Rectangle platform){
        if(platform.overlaps(doodleBounds) == true) return true;
        else return false;
    }

    /**
     * @brief Verifies if the doodle is colliding with a coin.
     * @param coin Coin rectangle to verify the collision.
     * @return Returns a true boolean if the doodle is colliding with a coin.
     */
    public boolean coinCollide(Rectangle coin){
        if(coin.overlaps(doodleBounds) == true){
            return true;
        }
        else return false;
    }

    /**
     * @brief Draws the platforms in the screen in the given position.
     * @param sb SpriteBatch necessary to draw the platforms.
     */
    public void render(SpriteBatch sb){
        sb.draw(normalDoodle,b2body.getPosition().x-32/DoodleJump.PPM, b2body.getPosition().y-32/DoodleJump.PPM,64/DoodleJump.PPM, 64/DoodleJump.PPM);
    }

    /**
     *@brief Disposes all the textures used.
     */
    public void dispose(){
        doodleNormal.dispose();
        doodleBunny.dispose();
        doodleJungle.dispose();
        doodleGhost.dispose();
        doodleIce.dispose();
        doodleSpace.dispose();
        doodleWater.dispose();
    }

    /**
     * @brief Makes the doodle jump to the right side.
     */
    public void jumpRight() {

        b2body.applyForceToCenter(-JUMP_WIDTH_MODIFIER, JUMP_HEIGHT_MODIFIER * -GRAVITY.y, true);
    }

    /**
     * @brief Makes the doodle jump to the left side.
     */
    public void jumpLeft(){
        b2body.applyForceToCenter(JUMP_WIDTH_MODIFIER, JUMP_HEIGHT_MODIFIER * -GRAVITY.y, true);
    }

    /**
     * @brief Returns the win boolean.
     * @return Returns the win boolean.
     */
    public boolean getWin() {
        return win;
    }

    /**
     * @brief Defines the win condition.
     * @param win Win condition.
     */
    public void setWin(boolean win) {
        this.win = win;
    }

    /**
     * @brief Defines the doodle integer texture for posterior use
     * @param texture Doodle integer texture.
     */
    public void setTexture(int texture){
        this.texture = texture;
    }

    /**
     * @brief Returns the play screen beign used.
     * @return Returns the play screen beign used.
     */
    public PlayScreen getScreen() {
        return screen;
    }
}

