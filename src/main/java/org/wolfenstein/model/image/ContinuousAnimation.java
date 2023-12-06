package org.wolfenstein.model.image;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.wolfenstein.model.Position;

public class ContinuousAnimation extends Animation {

    public ContinuousAnimation(Image sprite, Position position, int frameAmount) {
        super(sprite, position, frameAmount);
    }

    @Override
    protected void nextFrame() {
        frameCounter++;
        if (frameCounter >= frameAmount) {
            playing = false;
        } else {
            currentFrame = frames.get(frameCounter);
        }
    }


    @Override
    public void draw(TextGraphics graphics) {
        if (playing) {
            currentFrame.draw(graphics);
            nextFrame();
        }
    }
}
