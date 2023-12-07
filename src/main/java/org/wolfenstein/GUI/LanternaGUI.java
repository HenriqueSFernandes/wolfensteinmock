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
import org.wolfenstein.model.image.AnimationLoader;
import org.wolfenstein.model.sound.SoundLoader;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LanternaGUI implements GUI {
    public static final TextColor.RGB BLACK = new TextColor.RGB(0, 0, 0);
    public static final TextColor.RGB WHITE = new TextColor.RGB(255, 255, 255);
    public static final TextColor.RGB GRAY = new TextColor.RGB(128, 128, 128);
    public static final TextColor.RGB BROWN = new TextColor.RGB(73, 42, 21);
    public static final TextColor.RGB BLUE = new TextColor.RGB(14, 28, 46);
    public static TextGraphics graphics;
    private final AnimationLoader animationLoader = AnimationLoader.getInstance();
    private final SoundLoader soundLoader = SoundLoader.getInstance();
    private TerminalScreen screen;

    public LanternaGUI(int WIDTH, int HEIGHT) throws IOException, URISyntaxException, FontFormatException {
        AWTTerminalFontConfiguration fontConfig = createGameFont();
        createScreen(HEIGHT, WIDTH, fontConfig);
    }

    // For testing purposes only
    public LanternaGUI(TerminalScreen screen, TextGraphics textGraphics) throws IOException {
        this.screen = screen;
        graphics = textGraphics;
        screen.startScreen();
    }

    private AWTTerminalFontConfiguration createGameFont() throws URISyntaxException, IOException, FontFormatException {
        URL resource = getClass().getClassLoader().getResource("fonts/square.ttf");
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        Font loadedFont = font.deriveFont(Font.PLAIN, 4);
        return AWTTerminalFontConfiguration.newInstance(loadedFont);
    }

    public void createScreen(int HEIGHT, int WIDTH, AWTTerminalFontConfiguration fontConfig) throws IOException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
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
        animationLoader.importMomentaryAnimation("pistol_firing.png", new Position(332, 176));
        soundLoader.importSound("gun_shot.wav");
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

        while (screen.pollInput() != null) ;
        if (keyStroke == null) return GUIAction.NONE;

        if (keyStroke.getKeyType() == KeyType.EOF) return GUIAction.QUIT;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q') return GUIAction.QUIT;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'w') return GUIAction.FRONT;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'd') return GUIAction.RIGHT;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 's') return GUIAction.BACK;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'a') return GUIAction.LEFT;

        if (keyStroke.getKeyType() == KeyType.Backspace) {
            animationLoader.getAnimation(0).play();
            soundLoader.getSound(0).play();
        }
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'e') return GUIAction.SELECT;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'p') return GUIAction.SKIP;

        return GUIAction.NONE;
    }

    public void refresh() throws IOException {

        screen.refresh();
    }

    public void drawMap(Map map) {
        Vector<Vector<Integer>> grid = map.getMap();
        int height = map.getHeight();
        int width = map.getWidth();
        int cellsize = map.getCellsize();
        graphics.setBackgroundColor(GRAY);
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width * 2, height), ' ');
        // Adjust the size of each cell (square) and border
        int borderSize = 1;
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                int cellValue = grid.get(y).get(x);
                TextColor cellColor;

                if (cellValue == 0) {
                    cellColor = WHITE;
                } else if (cellValue == 1) {
                    cellColor = BLACK;
                } else if (cellValue == 2) {
                    cellColor = new TextColor.RGB(0, 255, 0);
                } else if (cellValue == 3) {
                    cellColor = new TextColor.RGB(0, 255, 255);
                } else if (cellValue == 4) {
                    cellColor = new TextColor.RGB(255, 255, 0);
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
        graphics.setBackgroundColor(BLACK);
        graphics.putString(x, y, text);
    }

    public void drawPlayerCamera(Position playerPosition, Map map) {
        int CELLSIZE = map.getCellsize();
        int WIDTH = map.getWidth() * CELLSIZE;
        int HEIGHT = map.getHeight() * CELLSIZE;
        for (int x = 0; x < WIDTH; x++) {
            double rayAngle = playerPosition.getRayAngle(map, x);
            List<Position> line = playerPosition.createLine(rayAngle, map);

            for (Position point : line) {
                graphics.setCharacter((int) point.getX(), (int) point.getY(), ' ');
            }
            if (!line.isEmpty()) {
                Position collisionPoint = line.get(line.size() - 1);
                double distanceToWall = Math.sqrt(Math.pow(collisionPoint.getX() - playerPosition.getX(), 2) + Math.pow(collisionPoint.getY() - playerPosition.getY(), 2));
                distanceToWall *= Math.abs(Math.cos(Math.toRadians(rayAngle - playerPosition.getAngle())));
                graphics.setBackgroundColor(mapColor(distanceToWall));
                // red line = players direction
                if (rayAngle == playerPosition.getAngle()) {
                    graphics.setBackgroundColor(new TextColor.RGB(255, 0, 0));
                }

                int maxWallHeight = HEIGHT;
                int wallHeight = (int) ((HEIGHT * CELLSIZE) / distanceToWall);
                int drawStart = -wallHeight / 2 + HEIGHT / 2;
                if (drawStart < 0) drawStart = 0;
                int drawEnd = wallHeight / 2 + HEIGHT / 2;
                if (drawEnd >= HEIGHT) drawEnd = HEIGHT - 1;
                double wallX = Math.tan(Math.toRadians(rayAngle - playerPosition.getAngle()));

                graphics.drawLine(2 * WIDTH - x, drawStart, 2 * WIDTH - x, drawEnd, ' ');

            }

        }
        animationLoader.drawAllAnimations(graphics);
    }
    @Override
    public void drawFloor() {
        TerminalSize size = graphics.getSize();
        graphics.setBackgroundColor(BROWN);
        graphics.fillRectangle(new TerminalPosition(size.getColumns() / 2, size.getRows() / 2), new TerminalSize(size.getColumns() / 2, size.getRows() / 2), ' ');
    }
    @Override
    public void drawCeiling() {
        TerminalSize size = graphics.getSize();
        graphics.setBackgroundColor(BLUE);
        graphics.fillRectangle(new TerminalPosition(size.getColumns() / 2, 0), new TerminalSize(size.getColumns() / 2, size.getRows() / 2), ' ');
    }
    TextColor.RGB mapColor(double distance) {
        int brightness = (int) Math.ceil(-0.9 * distance + 255);
        if (brightness < 0) brightness = 0;
        return new TextColor.RGB(brightness, brightness, brightness);
    }
    public void drawGuard(Position position, Map map) {
        int CELLSIZE = map.getCellsize();
        int WIDTH = map.getWidth() * CELLSIZE;
        for (int x = 0; x < WIDTH; x++) {
            double rayAngle = position.getRayAngle(map, x);
            List<Position> line = position.createLine(rayAngle, map);

            // Raycaster Render
            for (Position point : line) {
                graphics.setCharacter((int) point.getX(), (int) point.getY(), ' ');
            }
        }
    }
}
