package com.tq.doodle.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Inês on 04/06/2016.
 */
public class Coin{

    Array<TextureRegion> frames;
    float maxFrameTime;
    float currFrameTime;
    int frameCount;
    int frame;

    public Coin(TextureRegion region, int frameCount, float time){
        frames = new Array<TextureRegion>();
        TextureRegion temp;
        int frameWidth = region.getRegionWidth() / frameCount;

        for(int i=0; i <6; i++){
            temp = new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight());
            frames.add(temp);
        }

        this.frameCount=frameCount;
        maxFrameTime = time / frameCount;
    }


    public void update(float dt){
        currFrameTime += dt;

       if (currFrameTime > maxFrameTime){
           frame++;
           if(frame == 6) frame = 1;
           currFrameTime = 0;
       }
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

    public void dispose(){
        for (TextureRegion frame : frames){
            frame.getTexture().dispose();
        }
    }

}
