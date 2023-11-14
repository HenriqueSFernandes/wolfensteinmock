package org.engine;

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
import org.entities.Player;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Window {
    public static final Map gameMap = new Map();
    public static final int CELLSIZE = 8;
    public static final int WIDTH = gameMap.getWidth() * CELLSIZE; //16 is the cellSize;
    public static final int HEIGHT = gameMap.getHeight() * CELLSIZE;
    static final int TPS = 60;
    private final TerminalScreen screen;
    public static final TextColor.RGB BLACK = new TextColor.RGB(0, 0, 0);
    public static final TextColor.RGB WHITE = new TextColor.RGB(255, 255, 255);
    public static final TextColor.RGB GRAY = new TextColor.RGB(128, 128, 128);
    Player player = new Player(new Position(20, 20));

    public Window() throws IOException, URISyntaxException, FontFormatException {
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
        factory.setInitialTerminalSize(new TerminalSize(WIDTH * 2, HEIGHT));

        Terminal terminal = factory.createTerminal();
        ((AWTTerminalFrame) terminal).addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
        ((AWTTerminalFrame) terminal).setTitle("Wolfenstein Mock");
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);   // we don't need a cursor
        screen.startScreen();             // screens must be started
        screen.doResizeIfNecessary();     // resize screen if necessary
    }

    private void processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
            screen.stopScreen();
        } else {
            switch (key.getKeyType()) {
                case ArrowLeft -> player.rotateAntiClockwise();
                case ArrowRight -> player.rotateClockwise();
                case ArrowUp -> player.moveForward();
                case ArrowDown -> player.moveBackwards();
            }
        }
    }

    private void draw() throws IOException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(GRAY);
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(WIDTH * 2, HEIGHT), ' ');
        // Adjust the size of each cell (square) and border
        int borderSize = 1;

        int[][] map = gameMap.getMap();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                int cellValue = map[y][x];
                TextColor cellColor;

                if (cellValue == 0) {
                    cellColor = WHITE;
                } else {
                    cellColor = BLACK;
                }
//                  RENDER GRID
                for (int i = 0; i < CELLSIZE; i++) {
                    for (int j = 0; j < CELLSIZE; j++) {
                        // Draw the border
                        if (i < borderSize || i >= CELLSIZE - borderSize || j < borderSize || j >= CELLSIZE - borderSize) {
                            graphics.setBackgroundColor(GRAY);
                        } else {
                            graphics.setBackgroundColor(cellColor);
                        }

                        // Draw the square
                        graphics.putString(x * CELLSIZE + i, y * CELLSIZE + j, " ");
                    }
                }
            }
        }
        player.draw(graphics);

        screen.refresh();
    }

    public void run() throws IOException, InterruptedException {
        Input input = new Input(screen);
        input.start();
        outerLoop:
        while (true) {
            long start = System.currentTimeMillis();
            draw();
            while (!input.keyStrokeQueue.isEmpty()) {
                KeyStroke key = input.keyStrokeQueue.poll();
                processKey(key);
                if (key.getKeyType() == KeyType.EOF) {
                    input.interrupt();
                    break outerLoop;
                }
            }
            long end = System.currentTimeMillis();
            Thread.sleep(1000 / TPS - start + end);
        }
    }
}
