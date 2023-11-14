package org.entities;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.engine.Position;
import org.engine.Window;

import java.util.ArrayList;
import java.util.List;

public class Player {
    Position position;
    double angle;

    public Player(Position position) {
        this.position = position;
        angle = 0;
    }

    public void draw(TextGraphics graphics) {
        //int lineX = position.getX() + (int) (10 * Math.cos(Math.toRadians(angle)));
        //int lineY = position.getY() - (int) (10 * Math.sin(Math.toRadians(angle)));
        //graphics.setBackgroundColor(TextColor.Factory.fromString("#00FF00"));
        //graphics.drawLine(position.getX(), position.getY(), lineX, lineY, ' ');
        rayCaster(graphics);
        //graphics.setBackgroundColor(TextColor.Factory.fromString("#808080"));
        //graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFF00"));
        //graphics.setCharacter(position.getX(), position.getY(), ' ');
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
        double deltaX = 2 * Math.cos(Math.toRadians(angle + 180));
        double deltaY = 2 * Math.sin(Math.toRadians(angle + 180));

        if (checkCollisions((int) (position.getX() + deltaX), (int) (position.getY() - deltaY))) {
            position.setX((int) (position.getX() + deltaX));
            position.setY((int) (position.getY() - deltaY));
        }
    }

    public void rotateClockwise() {
        angle = (angle - 10 + 360) % 360;  // 10 is the rotation speed
    }

    public void rotateAntiClockwise() {
        angle = (angle + 10) % 360;
    }

    private void rayCaster(TextGraphics graphics) {
        // TODO change the algorithm to a DDA algorithm if it's better.
        // TODO fix fisheye effect.
        for (int x = 0; x < Window.WIDTH; x++) {
            double rayAngle = angle - 35 + (70.0 * x / Window.WIDTH);
            List<Position> line = createLine(rayAngle);

            // Raycaster Render
            for (Position point : line) {
                graphics.setCharacter(point.getX(), point.getY(), ' ');
            }
            Position collisionPoint = line.get(line.size() - 1);
            double distanceToWall = Math.sqrt(Math.pow(collisionPoint.getX() - position.getX(), 2) + Math.pow(collisionPoint.getY() - position.getY(), 2));
            distanceToWall *= Math.abs(Math.cos(Math.toRadians(rayAngle - angle))); // TODO Fisheye correction not working when the angle is 90 or 270.
            int color = mapColor(distanceToWall);
            graphics.setBackgroundColor(TextColor.Factory.fromString("#0000" + String.format("%02X", color)));
            int maxWallHeight = Window.HEIGHT;
            int wallHeight = (int) ((Window.HEIGHT * Window.CELLSIZE) / distanceToWall);
            int drawStart = -wallHeight / 2 + Window.HEIGHT / 2;
            if (drawStart < 0) drawStart = 0;
            int drawEnd = wallHeight / 2 + Window.HEIGHT / 2;
            if (drawEnd >= Window.HEIGHT) drawEnd = Window.HEIGHT - 1;
            double wallX = Math.tan(Math.toRadians(rayAngle - angle));

            // Map wallX to the screen width
            int wallScreenX = (int) (Window.WIDTH * (0.5 + wallX / 2.0));

            graphics.drawLine(2 * Window.WIDTH - wallScreenX, drawStart, 2 * Window.WIDTH - wallScreenX, drawEnd, ' ');
        }
    }

    private boolean checkCollisions(int x, int y) {
        return (Window.gameMap.getValue(x / Window.CELLSIZE, y / Window.CELLSIZE) != 1);
    }

    public List<Position> createLine(double angle) {
        // Creates a line using the Bresenham's line algorithm.
        List<Position> line = new ArrayList<Position>();
        int x1 = position.getX();
        int y1 = position.getY();
        int distance = 1000;
        int x2 = (int) (x1 + distance * Math.cos(Math.toRadians(angle)));
        int y2 = (int) -(y1 + distance * Math.sin(Math.toRadians(angle)));
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int side = 1; // 1 for top/bottom, 2 for left/right

        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            if (!checkCollisions(x1, y1)) {
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

    int mapColor(double distance) {
        // TODO make this function safer (add checks for making sure the value is between something and 255).
        return (int) Math.ceil(-0.400 * distance + 255);
    }


}
