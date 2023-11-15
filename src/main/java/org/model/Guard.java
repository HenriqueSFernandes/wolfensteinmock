package org.model;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Guard extends Movable implements Drawable {

    public Guard(Position position) {
        this.position = position;
        this.angle = 0;
    }

    @Override
    public void draw(TextGraphics graphics) {
        int lineX = position.getX() + (int) (10 * Math.cos(Math.toRadians(angle)));
        int lineY = position.getY() - (int) (10 * Math.sin(Math.toRadians(angle)));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#00FFFF"));
        graphics.drawLine(position.getX(), position.getY(), lineX, lineY, ' ');
        //graphics.setBackgroundColor(TextColor.Factory.fromString("#808080"));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#808080"));
        graphics.setCharacter(position.getX(), position.getY(), ' ');
    }
}
