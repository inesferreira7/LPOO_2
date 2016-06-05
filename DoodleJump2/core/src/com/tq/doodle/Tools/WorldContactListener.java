package com.tq.doodle.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tq.doodle.DoodleJump;

/**
 * Created by Inês on 05/06/2016.
 */
public class WorldContactListener implements ContactListener{


    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if((fixtureA.getFilterData().categoryBits == DoodleJump.DOODLE_BIT && fixtureB.getFilterData().categoryBits == DoodleJump.WORLD_BIT)
                || (fixtureA.getFilterData().categoryBits == DoodleJump.WORLD_BIT && fixtureB.getFilterData().categoryBits == DoodleJump.DOODLE_BIT))
            System.out.println("OlaInes!!!!!!!!!!!!!!!");
        }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void endContact(Contact contact) {

    }
}
