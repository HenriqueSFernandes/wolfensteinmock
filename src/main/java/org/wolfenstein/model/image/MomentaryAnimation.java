package org.wolfenstein.model.image;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.wolfenstein.model.Position;

public class MomentaryAnimation extends Animation {
    public MomentaryAnimation(Image sprite, Position position, int frameAmount) {
        super(sprite, position, frameAmount);
    }

    @Override
    protected void nextFrame() {
        frameCounter++;
        if (frameCounter >= frameAmount) {
            playing = false;
            frameCounter = 0;
        }
        currentFrame = frames.get(frameCounter);
    }

    @Override
    public void draw(TextGraphics graphics) {
        if (playing) {
            currentFrame.draw(graphics);
            nextFrame();
        } else {
            currentFrame.draw(graphics);
        }
    }

    @Override
    public void play() {
        this.playing = true;
    }

}
