package org.wolfenstein.model;

import java.util.ArrayList;
import java.util.List;

public class Position {
    double x, y;
    double angle;
    private static final int FOV = 70;

    public Position(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        this.angle = 0;
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

    public List<Position> lookForward() {
        double deltaX = 2 * Math.cos(Math.toRadians(angle));
        double deltaY = 2 * Math.sin(Math.toRadians(angle));
        List<Position> positionList = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            positionList.add(new Position (x + deltaX * i, y - deltaY * i, this.angle));
        }
        return positionList;
    }

    public Position rotateClockwise() {
        return new Position(this.x, this.y, (angle - 10 + 360) % 360.0);  // 10 is the rotation speed
    }

    public Position rotateAntiClockwise() {
        return new Position(this.x, this.y, (angle + 10) % 360.0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public double getRayAngle(Map map, int diff) {
        int CELLSIZE = map.getCellsize();
        int WIDTH = map.getWidth() * CELLSIZE;
        return (this.angle - (double) FOV / 2 + ((double) (FOV * diff) / WIDTH));
    }

    public List<Position> createLine(double angle, Map map) {
        // Creates a line using the Bresenham's line algorithm.
        List<Position> line = new ArrayList<>();
        int x1 = (int) this.x;
        int y1 = (int) this.y;
        int distance = 1000;
        int x2 = (int) (x1 + distance * Math.cos(Math.toRadians(angle)));
        int y2 = (int) (y1 + distance * Math.sin(Math.toRadians(angle)));
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? -1 : 1;

        int side = 1; // 1 for top/bottom, 2 for left/right

        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            if (map.getXY(x1 / map.getCellsize(), y1 / map.getCellsize()) == 1) {
                return line;
            }
            line.add(new Position(x1, y1));

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
                side = 2;

            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
                side = 1;
            }
        }
        return line;
    }

    public boolean inFOV(Position position) {
        double distance = Math.sqrt(Math.pow(position.getY() - this.y, 2) + Math.pow(position.getX() - this.x, 2));
        if (distance > 60) return false;
        double observer_direction = Math.toDegrees(Math.atan2(position.getY() - this.y, position.getX() - this.x));
        if (observer_direction < 0) observer_direction *= -1;
        else observer_direction = 360 - observer_direction;
        double angle_between = observer_direction - this.angle;
        angle_between = (angle_between + 180) % 360 - 180;

        return (-FOV / 2.0 <= angle_between && angle_between <= FOV / 2.0);
    }
}
