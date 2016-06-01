package com.tq.doodle.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.Screens.PlayScreen;

/**
 * Created by Inês on 01/06/2016.
 */
public class Doodle extends Sprite{
    public World world;
    public Body b2body;
    private TextureRegion jumpright;

    public Doodle(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("doodleright"));
        this.world = world;
        defineDoodle();
        jumpright = new TextureRegion(getTexture(), 0, 0, 60, 62);
        setBounds(0,0, 60/DoodleJump.PPM, 62/DoodleJump.PPM);
        setRegion(jumpright);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }


    public void defineDoodle(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(100/ DoodleJump.PPM, 30/ DoodleJump.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / DoodleJump.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }

}
