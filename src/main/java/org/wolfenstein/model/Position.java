package org.wolfenstein.model;

public class Position {
    double x, y;
    double angle;

    public Position(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public Position moveForward() {
        double deltaX = 2 * Math.cos(Math.toRadians(angle));
        double deltaY = 2 * Math.sin(Math.toRadians(angle));
        return new Position(x + deltaX, y - deltaY, this.angle);
    }
    public Position moveBackwards() {
        double deltaX = 2 * Math.cos(Math.toRadians(angle));
        double deltaY = 2 * Math.sin(Math.toRadians(angle));
        return new Position(x - deltaX, y + deltaY, this.angle);
    }
    public Position rotateClockwise() {
        return new Position(this.x, this.y, (angle - 10 + 360) % 360.0);  // 10 is the rotation speed
    }
    public Position rotateAntiClockwise() {
        return new Position(this.x, this.y, (angle + 10) % 360.0);
    }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getAngle() { return angle; }
}
