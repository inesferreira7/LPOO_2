package com.tq.doodle.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tq.doodle.DoodleJump;

/**
 * Created by Inês on 01/06/2016.
 */
public class Doodle extends Sprite{
    public World world;
    public Body b2body;

    public Doodle(World world){
        this.world = world;
        defineDoodle();
    }

    public void defineDoodle(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(30/ DoodleJump.PPM, 30/ DoodleJump.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / DoodleJump.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }

}
