package org.wolfenstein.model.image;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.wolfenstein.model.Position;

public class MomentaryAnimation extends Animation{
    public MomentaryAnimation(Image sprite, Position position, int frameAmount) {
        super(sprite, position, frameAmount);
    }

    @Override
    protected void nextFrame() {

    }

    @Override
    public void draw(TextGraphics graphics) {

    }
}
