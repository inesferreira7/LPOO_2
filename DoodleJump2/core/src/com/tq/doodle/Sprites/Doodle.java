package com.tq.doodle.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.Screens.PlayScreen;

import org.w3c.dom.css.Rect;


/**
 * Created by Inês on 01/06/2016.
 */
public class Doodle extends Sprite{
    public World world;
    public Body b2body;
    public boolean kk;
    private TextureRegion jumpright;
    public Rectangle doodleBounds;

    public final static Vector2 GRAVITY = new Vector2(0, -10);
    private final static int JUMP_HEIGHT_MODIFIER = 30;
    private final static int JUMP_WIDTH_MODIFIER = 120;
    private final static Vector2 base = new Vector2(0,0);

    public Doodle(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("doodleright"));
        kk = false;
        this.world = world;
        doodleBounds = new Rectangle();
        defineDoodle();
        jumpright = new TextureRegion(getTexture(), 0, 0, 60, 62);
        setBounds(0,0, 60/DoodleJump.PPM, 62/DoodleJump.PPM);
        setRegion(jumpright);
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
        //CircleShape shape = new CircleShape();
        //shape.setRadius(1 / DoodleJump.PPM);

        fdef.shape = shape;
        fdef.filter.categoryBits = DoodleJump.DOODLE_BIT;
        fdef.filter.maskBits = DoodleJump.GROUND_BIT | DoodleJump.WORLD_BIT;
        b2body.createFixture(fdef).setUserData(this);

        doodleBounds.set(b2body.getPosition().x-32/DoodleJump.PPM, b2body.getPosition().y-32/DoodleJump.PPM,64/DoodleJump.PPM, 64/DoodleJump.PPM);

    }

    public void flipDoodle(boolean turned, int screenX) {


            if (kk) {
                if(screenX >  DoodleJump.V_WIDTH/2){
                    flip(true, false);
                    kk = false;
                }
                if(screenX <  DoodleJump.V_WIDTH/2){
                    flip(false, false);
                    kk = true;

                }
            }
            if (!kk) {
                if(screenX >  DoodleJump.V_WIDTH/2){
                    flip(false, false);
                }
                if(screenX < DoodleJump.V_WIDTH/2){
                    turned = true;
                    kk = true;
                    flip(true, false);

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

    public void jumpRight() {

        b2body.applyForceToCenter(-JUMP_WIDTH_MODIFIER, JUMP_HEIGHT_MODIFIER * -GRAVITY.y, true);
    }

    public void jumpLeft(){
        b2body.applyForceToCenter(JUMP_WIDTH_MODIFIER, JUMP_HEIGHT_MODIFIER * -GRAVITY.y, true);
    }
}

