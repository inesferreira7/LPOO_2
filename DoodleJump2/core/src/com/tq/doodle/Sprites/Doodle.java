package com.tq.doodle.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.Screens.PlayScreen;

import org.w3c.dom.css.Rect;

import java.util.Random;


/**
 * Created by Inês on 01/06/2016.
 */
public class Doodle extends Sprite{
    public World world;
    public PlayScreen screen;
    public Body b2body;
    public boolean kk;

    //private TextureRegion jumpright;
    private Texture doodleNormal;
    private Texture doodleBunny;
    private Texture doodleJungle;

    private Texture doodleGhost;
    private Texture doodleIce;
    private Texture doodleSpace;
    private Texture doodleWater;
    private TextureRegion normalDoodle;

    private Texture tommyBiceps;
    private TextureRegion normalDoodle;

    private Random randDoodle;



    public Rectangle doodleBounds;

    public final static Vector2 GRAVITY = new Vector2(0, -10);
    private final static int JUMP_HEIGHT_MODIFIER = 30;
    private final static int JUMP_WIDTH_MODIFIER = 120;
    private final static Vector2 base = new Vector2(0,0);
    private boolean win ;
    private int texture;

    public Doodle(World world, PlayScreen screen){
       // super(screen.getAtlas().findRegion("doodleright"));
        kk = false;
        this.world = world;
        this.screen=screen;
        doodleBounds = new Rectangle();
        doodleNormal = new Texture("Jump.png");
        doodleBunny = new Texture("bunny.png");
        doodleJungle = new Texture("jungledoodle.png");
<<<<<<< HEAD
        doodleGhost = new Texture("ghost.png");
        doodleIce = new Texture("ice.png");
        doodleSpace = new Texture("space.png");
        doodleWater = new Texture("underwater.png");

=======
        tommyBiceps = new Texture("tommybiceps.png");
>>>>>>> origin/master
        chooseDoodle();

        defineDoodle();
        //jumpright = new TextureRegion(getTexture(), 0, 0, 60, 62);
        //setBounds(0,0, 60/DoodleJump.PPM, 62/DoodleJump.PPM);
        //setRegion(jumpright);
        win = false;
        texture =0;
    }

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

    public void flipDoodle(boolean turned, int screenX) {


            if (kk) {
                if(screenX >  DoodleJump.V_WIDTH/2){
                    normalDoodle.flip(true, false);
                    kk = false;
                }
                if(screenX <  DoodleJump.V_WIDTH/2){
                    normalDoodle.flip(false, false);
                    kk = true;

                }
            }
            if (!kk) {
                if(screenX >  DoodleJump.V_WIDTH/2){
                    normalDoodle.flip(false, false);
                }
                if(screenX < DoodleJump.V_WIDTH/2){
                    turned = true;
                    kk = true;
                    normalDoodle.flip(true, false);

                }
            }
        }

    public Rectangle getDoodleBounds() {
        return doodleBounds;
    }



    public boolean collides(Rectangle platform){
        if(platform.overlaps(doodleBounds) == true) return true;
        else return false;
    }

    public boolean coinCollide(Rectangle coin){
        if(coin.overlaps(doodleBounds) == true){
            return true;
        }
        else return false;
    }

    public void render(SpriteBatch sb){
        sb.draw(normalDoodle,b2body.getPosition().x-32/DoodleJump.PPM, b2body.getPosition().y-32/DoodleJump.PPM,64/DoodleJump.PPM, 64/DoodleJump.PPM);
    }

    public void dispose(){
        doodleNormal.dispose();
        doodleBunny.dispose();
        doodleJungle.dispose();
    }

    public void jumpRight() {

        b2body.applyForceToCenter(-JUMP_WIDTH_MODIFIER, JUMP_HEIGHT_MODIFIER * -GRAVITY.y, true);
    }

    public void jumpLeft(){
        b2body.applyForceToCenter(JUMP_WIDTH_MODIFIER, JUMP_HEIGHT_MODIFIER * -GRAVITY.y, true);
    }

    public boolean getWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setTexture(int texture){
        this.texture = texture;
    }

    public PlayScreen getScreen() {
        return screen;
    }
}

