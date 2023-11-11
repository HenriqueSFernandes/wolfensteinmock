package org.entities;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.engine.Position;
import org.engine.Window;

public class Player {
    Position position;
    double angle;

    public Player(Position position) {
        this.position = position;
        angle = 0;
    }

    public void draw(TextGraphics graphics) {
        int lineX = position.getX() + (int) (10 * Math.cos(Math.toRadians(angle)));
        int lineY = position.getY() - (int) (10 * Math.sin(Math.toRadians(angle)));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#00FF00"));
        graphics.drawLine(position.getX(), position.getY(), lineX, lineY, ' ');
        //graphics.setBackgroundColor(TextColor.Factory.fromString("#808080"));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFF00"));
        graphics.setCharacter(position.getX(), position.getY(), ' ');
    }

    public void moveForward() {
        double deltaX = 2 * Math.cos(Math.toRadians(angle));
        double deltaY = 2 * Math.sin(Math.toRadians(angle));

        if (checkCollisions((int) (position.getX() + deltaX), (int) (position.getY() - deltaY))) {
            position.setX((int) (position.getX() + deltaX));
            position.setY((int) (position.getY() - deltaY));
        }
    }

    public void moveBackwards() {
        double deltaX = 2 * Math.cos(Math.toRadians(angle));
        double deltaY = 2 * Math.sin(Math.toRadians(angle));
        if (checkCollisions((int) (position.getX() - deltaX), (int) (position.getY() + deltaY))) {
            position.setX((int) (position.getX() - deltaX));
            position.setY((int) (position.getY() + deltaY));
        }
    }

    public void rotateClockwise() {
        angle = (angle - 10 + 360) % 360;  // 10 is the rotation speed
    }

    public void rotateAntiClockwise() {
        angle = (angle + 10) % 360;
    }

    private boolean checkCollisions(int x, int y) {
        return (Window.gameMap.getValue(x / 16, y / 16) != 1); // 16 is the cell size
    }

}
