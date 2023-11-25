package org.wolfenstein.GUI;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import org.wolfenstein.model.Map;
import org.wolfenstein.model.Position;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LanternaGUI implements GUI {
    private TerminalScreen screen;
    public static final TextColor.RGB BLACK = new TextColor.RGB(0, 0, 0);
    public static final TextColor.RGB WHITE = new TextColor.RGB(255, 255, 255);
    public static final TextColor.RGB GRAY = new TextColor.RGB(128, 128, 128);
    public static TextGraphics graphics;

    public void createScreen(int WIDTH, int HEIGHT) throws IOException, URISyntaxException, FontFormatException {
        URL resource = getClass().getClassLoader().getResource("square.ttf");
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        Font loadedFont = font.deriveFont(Font.PLAIN, 4);
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);
        factory.setTerminalEmulatorFontConfiguration(fontConfig);
        factory.setForceAWTOverSwing(true);
        factory.setInitialTerminalSize(new TerminalSize(WIDTH, HEIGHT));

        Terminal terminal = factory.createTerminal();
        ((AWTTerminalFrame) terminal).addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
        ((AWTTerminalFrame) terminal).setTitle("Wolfenstein Mock");
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        graphics = screen.newTextGraphics();
    }

    public void stopScreen() throws IOException {
        screen.close();
    }

    public void clear() {
        screen.clear();
    }

    @Override
    public GUIAction getNextAction() throws IOException {
        KeyStroke keyStroke = screen.pollInput();
        if (keyStroke == null) return GUIAction.NONE;

        if (keyStroke.getKeyType() == KeyType.EOF) return GUIAction.QUIT;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q') return GUIAction.QUIT;

        if (keyStroke.getKeyType() == KeyType.ArrowUp) return GUIAction.FRONT;
        if (keyStroke.getKeyType() == KeyType.ArrowRight) return GUIAction.RIGHT;
        if (keyStroke.getKeyType() == KeyType.ArrowDown) return GUIAction.BACK;
        if (keyStroke.getKeyType() == KeyType.ArrowLeft) return GUIAction.LEFT;

        if (keyStroke.getKeyType() == KeyType.Enter) return GUIAction.SELECT;

        return GUIAction.NONE;
    }

    public void refresh() throws IOException {
        screen.refresh();
    }

    public void drawMap(Map map) {
        int[][] grid = map.getMap();
        int height = map.getHeight();
        int width = map.getWidth();
        int cellsize = map.getCellsize();
        graphics.setBackgroundColor(GRAY);
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width * 2, height), ' ');
        // Adjust the size of each cell (square) and border
        int borderSize = 1;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                int cellValue = grid[y][x];
                TextColor cellColor;

                if (cellValue == 0) {
                    cellColor = WHITE;
                } else {
                    cellColor = BLACK;
                }
//                  RENDER GRID
                for (int i = 0; i < cellsize; i++) {
                    for (int j = 0; j < cellsize; j++) {
                        // Draw the border
                        if (i < borderSize || i >= cellsize - borderSize || j < borderSize || j >= cellsize - borderSize) {
                            graphics.setBackgroundColor(GRAY);
                        } else {
                            graphics.setBackgroundColor(cellColor);
                        }

                        // Draw the square
                        graphics.putString(x * cellsize + i, y * cellsize + j, " ");
                    }
                }
            }
        }
    }

    public void drawText(int x, int y, String text) {
        graphics.putString(x, y, text);
    }

    public void drawPlayerCamera(Position playerPosition, Map map) {
        int CELLSIZE = map.getCellsize();
        int WIDTH = map.getWidth() * CELLSIZE;
        int HEIGHT = map.getHeight() * CELLSIZE;
        for (int x = 0; x < WIDTH; x++) {
            double rayAngle = playerPosition.getAngle() - 35 + (70.0 * x / WIDTH);
            List<Position> line = createLine(rayAngle, playerPosition, map);

            // Raycaster Render
            for (Position point : line) {
                graphics.setCharacter(point.getX(), point.getY(), ' ');
            }
            if (!line.isEmpty()) {
                Position collisionPoint = line.get(line.size() - 1);
                double distanceToWall = Math.sqrt(Math.pow(collisionPoint.getX() - playerPosition.getX(), 2) + Math.pow(collisionPoint.getY() - playerPosition.getY(), 2));
                distanceToWall *= Math.abs(Math.cos(Math.toRadians(rayAngle - playerPosition.getAngle()))); // TODO Fisheye correction not working when the angle is 90 or 270.
                graphics.setBackgroundColor(mapColor(distanceToWall));
                int maxWallHeight = HEIGHT;
                int wallHeight = (int) ((HEIGHT * CELLSIZE) / distanceToWall);
                int drawStart = -wallHeight / 2 + HEIGHT / 2;
                if (drawStart < 0) drawStart = 0;
                int drawEnd = wallHeight / 2 + HEIGHT / 2;
                if (drawEnd >= HEIGHT) drawEnd = HEIGHT - 1;
                double wallX = Math.tan(Math.toRadians(rayAngle - playerPosition.getAngle()));

                // Map wallX to the screen width
                int wallScreenX = (int) (WIDTH * (0.5 + wallX / 2.0));

                graphics.drawLine(2 * WIDTH - wallScreenX, drawStart, 2 * WIDTH - wallScreenX, drawEnd, ' ');
            }
        }
    }

    public List<Position> createLine(double angle, Position playerPosition, Map map) {
        // Creates a line using the Bresenham's line algorithm.
        List<Position> line = new ArrayList<>();
        int x1 = playerPosition.getX();
        int y1 = playerPosition.getY();
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
            if (map.getXY(x1 / map.getCellsize(), y1 / map.getCellsize()) == 1) {
                return line;
            }
            line.add(new Position(x1, y1, 0));

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
        if (brightness < 0) brightness = 0;
        return new TextColor.RGB(brightness, brightness, brightness);

    }


}