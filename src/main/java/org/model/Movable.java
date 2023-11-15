package org.model;

import org.view.Window;

public abstract class Movable {
    Position position;
    double angle;
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
    public boolean checkCollisions(int x, int y) {
        return (Window.gameMap.getValue(x / Window.CELLSIZE, y / Window.CELLSIZE) != 1);
    }
}
