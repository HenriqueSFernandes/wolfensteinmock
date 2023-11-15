package org.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.controller.GuardController;
import org.controller.LanternaInput;
import org.controller.PlayerController;
import org.model.Map;
import org.model.Position;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Window {

    private static Window window;
    public static final Map gameMap = new Map();
    private final ScreenDrawer screenDrawer;
    private final LanternaScreen screen;
    public static final int CELLSIZE = gameMap.getCellsize();
    public static final int WIDTH = gameMap.getWidth() * CELLSIZE;
    public static final int HEIGHT = gameMap.getHeight() * CELLSIZE;
    static final int TPS = 60;
    static PlayerController player;

    public Window() throws IOException, URISyntaxException, FontFormatException {
        screen = new LanternaScreen();
        screen.createScreen(WIDTH * 2, HEIGHT);
        screenDrawer = new ScreenDrawer(screen, gameMap);
        spawnEntities();
    }

    public static Window getWindow() throws IOException, URISyntaxException, FontFormatException {
        if (window == null) {
            window = new Window();
        }
        return window;
    }

    public void run() throws IOException, InterruptedException, URISyntaxException, FontFormatException {
        LanternaInput input = new LanternaInput(screen);
        input.start();
        outerLoop:
        while (true) {
            long start = System.currentTimeMillis();
            screenDrawer.draw();
            while (!input.keyStrokeQueue.isEmpty()) {
                KeyStroke key = input.keyStrokeQueue.poll();
                player.processKey(key);
                if (key.getKeyType() == KeyType.EOF) {
                    input.interrupt();
                    break outerLoop;
                }
            }
            long end = System.currentTimeMillis();
            Thread.sleep(1000 / TPS - start + end);
        }
    }

    public void spawnEntities() {
        player = new PlayerController(new Position(20, 20), screen);
        screenDrawer.addToDrawableEntities(player);
    }
}
