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
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Map;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Player;
import org.wolfenstein.model.image.AnimationLoader;
import org.wolfenstein.model.image.ImageLoader;
import org.wolfenstein.model.sound.SoundLoader;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class LanternaGUI implements GUI {
    public static final TextColor.RGB BLACK = new TextColor.RGB(0, 0, 0);
    public static final TextColor.RGB WHITE = new TextColor.RGB(255, 255, 255);
    public static final TextColor.RGB GRAY = new TextColor.RGB(128, 128, 128);
    public static final TextColor.RGB BROWN = new TextColor.RGB(73, 42, 21);
    public static final TextColor.RGB BLUE = new TextColor.RGB(14, 28, 46);
    public TextGraphics graphics;
    private final AnimationLoader animationLoader;
    private final SoundLoader soundLoader;
    private final ImageLoader imageLoader;
    private TerminalScreen screen;

    public LanternaGUI(int WIDTH, int HEIGHT) throws IOException, URISyntaxException, FontFormatException {
        AWTTerminalFontConfiguration fontConfig = createGameFont();
        animationLoader = AnimationLoader.getInstance();
        soundLoader = SoundLoader.getInstance();
        imageLoader = ImageLoader.getInstance();
        createScreen(HEIGHT, WIDTH, fontConfig);
    }

    public LanternaGUI(TerminalScreen screen, TextGraphics textGraphics, AnimationLoader animationLoader, SoundLoader soundLoader, ImageLoader imageLoader) {
        this.screen = screen;
        this.animationLoader = animationLoader;
        this.soundLoader = soundLoader;
        this.imageLoader = imageLoader;
        graphics = textGraphics;
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
        soundLoader.importSound("enemy_dying.wav");
        for (int i = 0; i < Player.getInstance().getMaxHealth(); i++) {
            imageLoader.importImage("heart.png", new Position(242 + 12 * i, 0));
        }
        imageLoader.importImage("aim.png", new Position(115 + 240, 115));
        imageLoader.importImage("menu_play.png", new Position(0, 0));
        imageLoader.importImage("menu_exit.png", new Position(0, 0));
        for (int i = 0; i < Camera.createCamera().getMaxGuardNumber(); i++) {
            imageLoader.importImage("enemy.png", new Position(332, 120));
        }
        for (int i = 0; i < Camera.createCamera().getMaxGuardNumber(); i++) {
            imageLoader.importImage("enemy.png", new Position(242 + 12 * i, 20));
        }
    }

    @Override
    public void stopScreen() throws IOException {
        screen.close();
    }

    @Override
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

        if (keyStroke.getKeyType() == KeyType.ArrowUp) {
            Player.getInstance().changeHealth(1);
            return GUIAction.NONE;
        }
        //if (keyStroke.getKeyType() == KeyType.ArrowRight) return GUIAction.RIGHT;
        if (keyStroke.getKeyType() == KeyType.ArrowDown) {
            Player.getInstance().changeHealth(-1);
            return GUIAction.NONE;
        }
        //if (keyStroke.getKeyType() == KeyType.ArrowLeft) return GUIAction.LEFT;


        if ((keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == ' ') && (animationLoader.getAnimation(0).getAnimation().get(0) == animationLoader.getAnimation(0).getCurrentFrame())) {
            animationLoader.getAnimation(0).play();
            soundLoader.getSound(0).play();
            return GUIAction.FIRE;
        }
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'e') return GUIAction.SELECT;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'p') return GUIAction.SKIP;

        return GUIAction.NONE;
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    @Override
    public void drawMap(Map map) {
        List<List<Integer>> grid = map.getMap();
        int height = map.getHeight();
        int width = map.getWidth();
        int cellsize = map.getCellsize();
        graphics.setBackgroundColor(GRAY);
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width * 2, height), ' ');
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
                } else if (cellValue == 5) {
                    cellColor = new TextColor.RGB(255, 0, 0);
                } else {
                    cellColor = BLACK;
                }

                for (int i = 0; i < cellsize; i++) {
                    for (int j = 0; j < cellsize; j++) {
                        if (i < borderSize || i >= cellsize - borderSize || j < borderSize || j >= cellsize - borderSize) {
                            graphics.setBackgroundColor(GRAY);
                        } else {
                            graphics.setBackgroundColor(cellColor);
                        }

                        graphics.putString(x * cellsize + i, y * cellsize + j, " ");
                    }
                }
            }
        }
    }

    @Override
    public void drawText(int x, int y, String text) {
        graphics.setBackgroundColor(BLACK);
        graphics.putString(x, y, text);
    }

    @Override
    public void drawPlayerCamera(Position playerPosition, Map map) throws IOException {
        int CELLSIZE = map.getCellsize();
        int WIDTH = map.getWidth() * CELLSIZE;
        int HEIGHT = map.getHeight() * CELLSIZE;
        for (int x = 0; x < WIDTH; x++) {
            double rayAngle = playerPosition.getRayAngle(map, x);
            List<Position> line = playerPosition.createLine(rayAngle, map);
            List<Position> doorLine = playerPosition.createLineForDoor(rayAngle, map);

            for (Position point : line) {
                graphics.setCharacter((int) point.getX(), (int) point.getY(), ' ');
            }
            if (!line.isEmpty()) {
                Position collisionPoint = line.get(line.size() - 1);
                double distanceToWall = Math.sqrt(Math.pow(collisionPoint.getX() - playerPosition.getX(), 2) + Math.pow(collisionPoint.getY() - playerPosition.getY(), 2));
                distanceToWall *= Math.abs(Math.cos(Math.toRadians(rayAngle - playerPosition.getAngle())));
                graphics.setBackgroundColor(mapColor(distanceToWall));
                if (rayAngle == playerPosition.getAngle()) {
                    graphics.setBackgroundColor(new TextColor.RGB(255, 0, 0));
                }

                int wallHeight = (int) ((HEIGHT * CELLSIZE) / distanceToWall);
                int drawStart = -wallHeight / 2 + HEIGHT / 2;
                if (drawStart < 0) drawStart = 0;
                int drawEnd = wallHeight / 2 + HEIGHT / 2;
                if (drawEnd >= HEIGHT) drawEnd = HEIGHT - 1;

                graphics.drawLine(2 * WIDTH - x, drawStart, 2 * WIDTH - x, drawEnd, ' ');

            }
            for (Position point : doorLine) {
                graphics.setCharacter((int) point.getX(), (int) point.getY(), ' ');
            }
            if (!doorLine.isEmpty()) {
                Position collisionPoint = doorLine.get(doorLine.size() - 1);
                double distanceToWall = Math.sqrt(Math.pow(collisionPoint.getX() - playerPosition.getX(), 2) + Math.pow(collisionPoint.getY() - playerPosition.getY(), 2));
                distanceToWall *= Math.abs(Math.cos(Math.toRadians(rayAngle - playerPosition.getAngle())));
                graphics.setBackgroundColor(mapColor(distanceToWall));

                int wallHeight = (int) ((HEIGHT * CELLSIZE) / distanceToWall);
                int drawStart = -wallHeight / 2 + HEIGHT / 2;
                if (drawStart < 0) drawStart = 0;
                int drawEnd = wallHeight / 2 + HEIGHT / 2;
                if (drawEnd >= HEIGHT) drawEnd = HEIGHT - 1;

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

    @Override
    public void drawHearts() {
        for (int i = 0; i < 10; i++) {
            imageLoader.getImage(i).draw(graphics);
        }
    }

    @Override
    public void drawAim() {
        imageLoader.getImage(10).draw(graphics);
    }

    @Override
    public TextGraphics getGraphics() {
        return graphics;
    }

    TextColor.RGB mapColor(double distance) {
        int brightness = (int) Math.ceil(-2.5 * distance + 255);
        if (brightness < 0) brightness = 0;
        return new TextColor.RGB(brightness, brightness, brightness);
    }

    @Override
    public void drawGuard(int index, Position position, Map map) throws IOException {
        int CELLSIZE = map.getCellsize();
        int WIDTH = map.getWidth() * CELLSIZE;
        for (int x = 0; x < WIDTH; x++) {
            double rayAngle = position.getRayAngle(map, x);
            List<Position> line = position.createLine(rayAngle, map);
            List<Position> doorLine = position.createLineForDoor(rayAngle, map);
            graphics.setBackgroundColor(new TextColor.RGB(255, 0, 0));
            for (Position point : line) {
                graphics.setCharacter((int) point.getX(), (int) point.getY(), ' ');
            }
            for (Position point : doorLine) {
                graphics.setCharacter((int) point.getX(), (int) point.getY(), '@');
            }
        }
        imageLoader.getImage(13 + index).setPosition(new Position(350 - 2.9 * (int) Player.getInstance().getPosition().viewAngle(position), 130 - position.distance(Player.getInstance().getPosition()) / 2.0));
        imageLoader.getImage(13 + index).draw(graphics);
    }

    @Override
    public void drawGuardCounter() throws IOException {
        for (int i = 0; i < Camera.createCamera().getGuardList().size(); i++) {
            if (Camera.createCamera().getGuardList().get(i).getHealth() > 0)
                imageLoader.getImage(13 + Camera.createCamera().getMaxGuardNumber() + i).draw(graphics);
        }
    }
}
