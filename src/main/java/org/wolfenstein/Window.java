package org.wolfenstein;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Window {
    static final int WIDTH = 640;
    static final int HEIGHT = 480;
    static final int TPS = 60;
    private final TerminalScreen screen;
    static final String BLACK = "#000000";
    static final String WHITE = "#FFFFFF";
    static final String GRAY = "#808080";
    static final Map gameMap = new Map();

    public Window() throws IOException, URISyntaxException, FontFormatException {
        URL resource = getClass().getClassLoader().getResource("square.ttf");
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        Font loadedFont = font.deriveFont(Font.PLAIN, 2);
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
        screen.setCursorPosition(null);   // we don't need a cursor
        screen.startScreen();             // screens must be started
        screen.doResizeIfNecessary();     // resize screen if necessary
    }

    private void processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
            screen.stopScreen();
        } //else {
//            switch (key.getKeyType()) {
//
//                case ArrowUp ->
//                case ArrowDown ->
//                //case ArrowLeft ->
//                //case ArrowRight ->
//            }
//        }
    }

    private void draw() throws IOException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(GRAY));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(WIDTH, HEIGHT), ' ');
        // Adjust the size of each cell (square) and border
        int cellSize = 64;
        int borderSize = 1;

        int[][] map = gameMap.getMap();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                int cellValue = map[y][x];
                TextColor cellColor;

                if (cellValue == 0) {
                    cellColor = TextColor.Factory.fromString(WHITE);
                } else {
                    cellColor = TextColor.Factory.fromString(BLACK);
                }

                for (int i = 0; i < cellSize; i++) {
                    for (int j = 0; j < cellSize; j++) {
                        // Draw the border
                        if (i < borderSize || i >= cellSize - borderSize || j < borderSize || j >= cellSize - borderSize) {
                            graphics.setBackgroundColor(TextColor.Factory.fromString(GRAY));
                        } else {
                            graphics.setBackgroundColor(cellColor);
                        }

                        // Draw the square
                        graphics.putString(x * cellSize + i, y * cellSize + j, " ");
                    }
                }
            }
        }

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
