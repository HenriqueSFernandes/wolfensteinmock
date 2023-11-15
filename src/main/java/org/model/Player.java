package org.model;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.view.Window;
import java.util.List;
import java.util.ArrayList;

public class Player extends Movable implements Drawable {

    public Player(Position position) {
        this.position = position;
        angle = 0;
    }
    @Override
    public void draw(TextGraphics graphics) {
        rayCaster(graphics);
    }
    private void rayCaster(TextGraphics graphics) {
        // TODO change the algorithm to a DDA algorithm if it's better.
        // TODO fix fisheye effect.
        for (int x = 0; x < Window.WIDTH; x++) {
            double rayAngle = this.angle - 35 + (70.0 * x / Window.WIDTH);
            List<Position> line = createLine(rayAngle);

            // Raycaster Render
            for (Position point : line) {
                graphics.setCharacter(point.getX(), point.getY(), ' ');
            }
            Position collisionPoint = line.get(line.size() - 1);
            double distanceToWall = Math.sqrt(Math.pow(collisionPoint.getX() - this.position.getX(), 2) + Math.pow(collisionPoint.getY() - this.position.getY(), 2));
            distanceToWall *= Math.abs(Math.cos(Math.toRadians(rayAngle - this.angle))); // TODO Fisheye correction not working when the angle is 90 or 270.
            graphics.setBackgroundColor(mapColor(distanceToWall));
            int maxWallHeight = Window.HEIGHT;
            int wallHeight = (int) ((Window.HEIGHT * Window.CELLSIZE) / distanceToWall);
            int drawStart = -wallHeight / 2 + Window.HEIGHT / 2;
            if (drawStart < 0) drawStart = 0;
            int drawEnd = wallHeight / 2 + Window.HEIGHT / 2;
            if (drawEnd >= Window.HEIGHT) drawEnd = Window.HEIGHT - 1;
            double wallX = Math.tan(Math.toRadians(rayAngle - this.angle));

            // Map wallX to the screen width
            int wallScreenX = (int) (Window.WIDTH * (0.5 + wallX / 2.0));

            graphics.drawLine(2 * Window.WIDTH - wallScreenX, drawStart, 2 * Window.WIDTH - wallScreenX, drawEnd, ' ');
        }
    }
    public List<Position> createLine(double angle) {
        // Creates a line using the Bresenham's line algorithm.
        List<Position> line = new ArrayList<>();
        int x1 = this.position.getX();
        int y1 = this.position.getY();
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

    TextColor.RGB mapColor(double distance) {
        // TODO make this function safer (add checks for making sure the value is between something and 255).
        int brightness = (int)Math.ceil(-0.9 * distance + 255);
        return new TextColor.RGB(brightness, brightness, brightness);

    }

}
