package org.entities;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.engine.Position;

public class Player {
    Position position;
    double angle;

    public Player(Position position) {
        this.position = position;
        angle = 0;
    }

    public void draw(TextGraphics graphics) {
        graphics.putString(position.getX(), position.getY(), "Player");
        int lineX = position.getX() + (int) (10 * Math.cos(Math.toRadians(angle)));
        int lineY = position.getY() - (int) (10 * Math.sin(Math.toRadians(angle)));
        graphics.drawLine(position.getX(), position.getY(), lineX, lineY, ' ');
    }

    public void moveForward() {
        double deltaX = Math.cos(Math.toRadians(angle));
        double deltaY = Math.sin(Math.toRadians(angle));

        position.setX((int) (position.getX() + deltaX));
        position.setY((int) (position.getY() + deltaY));
    }

    public void moveBackwards() {
        double deltaX = Math.cos(Math.toRadians(angle));
        double deltaY = Math.sin(Math.toRadians(angle));

        position.setX((int) Math.floor(position.getX() - deltaX));
        position.setY((int) Math.floor(position.getY() - deltaY));
    }

    public void rotateClockwise() {
        angle = (angle - 10 + 360) % 360;  // 10 is the rotation speed
    }

    public void rotateAntiClockwise() {
        angle = (angle + 10) % 360;
    }

}
