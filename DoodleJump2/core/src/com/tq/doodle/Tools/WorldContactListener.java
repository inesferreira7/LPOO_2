package com.tq.doodle.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.GUI.PlayScreen;
import com.tq.doodle.Logic.Coin;
import com.tq.doodle.Logic.Doodle;

/**
 * Created by Inês on 05/06/2016.
 */
public class WorldContactListener implements ContactListener{

    private World world;
    private Coin coin;
    private PlayScreen screen;
    private DoodleJump game;
    private boolean vitoria;

    public WorldContactListener( World world, Coin coin, PlayScreen screen){
        this.world = world;
        this.coin = coin;
        this.screen=screen;
        vitoria = false;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        //Colisao entre o doodle e a meta
        if ((fixtureA.getFilterData().categoryBits == DoodleJump.DOODLE_BIT && fixtureB.getFilterData().categoryBits == DoodleJump.WORLD_BIT)){
            //coin.winRender(game.batch);
            ((Doodle) fixtureA.getUserData()).setWin(true);

        } else if (fixtureA.getFilterData().categoryBits == DoodleJump.WORLD_BIT && fixtureB.getFilterData().categoryBits == DoodleJump.DOODLE_BIT)
        {
            //coin.winRender(game.batch);
            ((Doodle) fixtureB.getUserData()).setWin(true);
        }


        //Implementar colisao entre doodle e moeda
        if (fixtureA.getFilterData().categoryBits == DoodleJump.DOODLE_BIT && fixtureB.getFilterData().categoryBits == DoodleJump.COIN_BIT){
            /*((Coin))
            for(int i = 0; i < ((Coin)fixtureB.getUserData()).getCoins().size(); i++){

            }*/

    }

        else if (fixtureA.getFilterData().categoryBits == DoodleJump.COIN_BIT && fixtureB.getFilterData().categoryBits == DoodleJump.DOODLE_BIT) {
            System.out.println("Reconhece colisao entre doodle e moeda");
        }
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

    public boolean getVitoria(){
        return vitoria;
    }
}
