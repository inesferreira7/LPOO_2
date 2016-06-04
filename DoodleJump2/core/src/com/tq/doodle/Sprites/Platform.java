package com.tq.doodle.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tq.doodle.DoodleJump;
import com.tq.doodle.Screens.PlayScreen;
import java.util.Random;

/**
 * Created by Utilizador on 02/06/2016.
 */
public class Platform extends Sprite {
    private static final int XVAR = DoodleJump.V_WIDTH ;
    private static final int YVAR = DoodleJump.V_HEIGHT;

    public World world;
    public Body platBody;
    private Random randX;
    private Random randY;
    private Random randGap;
    private static int currentHeight = 0;
    private Texture newPlat;
    private Array<Rectangle> rectangles;
    public Rectangle bounds;
    private Array<Body> platforms;
    private Texture teste;

    public Platform(World world, PlayScreen screen){
        this.world = world;
        newPlat = new Texture("plat.png");
        teste = new Texture("teste.png");
        platforms = new Array<Body>();
        rectangles = new Array<Rectangle>();
        bounds = new Rectangle();

        currentHeight = 0;

        while(screen.getMapHeight() >= currentHeight){
            randGap = new Random();
            double gap;
            gap = randGap.nextInt(200-150)+100 ;
            currentHeight+=gap;
            definePlatform(currentHeight);
        }
        System.out.println("SIZE: " + platforms.size);
    }

    public void definePlatform(double currentHeight){
        randX = new Random();
        randY = new Random();
        bounds =  new Rectangle(randX.nextInt(XVAR)/DoodleJump.PPM,(int)currentHeight/DoodleJump.PPM,32/DoodleJump.PPM,8/DoodleJump.PPM);
        rectangles.add(bounds);


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32/ DoodleJump.PPM,8/DoodleJump.PPM);

        BodyDef bdef = new BodyDef();
        bdef.position.set(randX.nextInt(XVAR) / DoodleJump.PPM, (int)currentHeight/DoodleJump.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        platBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        shape.setAsBox(32/ DoodleJump.PPM,8/DoodleJump.PPM);

        fdef.shape = shape;

        platBody.createFixture(fdef);
        bounds.set(platBody.getPosition().x,platBody.getPosition().y,64/DoodleJump.PPM, 16/DoodleJump.PPM);

        platforms.add(platBody);
    }

    public void render(SpriteBatch sb){
        for(int i = 0; i < platforms.size -1; i++){
           // System.out.println("ENTROU!");
            sb.draw(newPlat,platforms.get(i).getPosition().x-32/DoodleJump.PPM,platforms.get(i).getPosition().y-8/DoodleJump.PPM,64/DoodleJump.PPM,16/DoodleJump.PPM);
        }
    }

    public Array<Rectangle> getRectangles() {
        return rectangles;
    }

    public Array<Body> getPlatforms() {
        return platforms;
    }
}
