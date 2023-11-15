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
    public static final Map gameMap = new Map();
    private final ScreenDrawer screenDrawer;
    private final LanternaScreen screen;
    static final int WIDTH = gameMap.getWidth() * 16; //16 is the cellSize;
    static final int HEIGHT = gameMap.getHeight() * 16;
    static final int TPS = 60;
    static PlayerController player;
    static GuardController guard;

    public Window() throws IOException, URISyntaxException, FontFormatException {
        screen = new LanternaScreen();
        screen.createScreen(WIDTH, HEIGHT);
        screenDrawer = new ScreenDrawer(screen, gameMap);
        spawnEntities();
    }

    public void run() throws IOException, InterruptedException {
        LanternaInput input = new LanternaInput(screen);
        input.start();
        outerLoop:
        while (true) {
            long start = System.currentTimeMillis();
            screenDrawer.draw();
            while (!input.keyStrokeQueue.isEmpty()) {
                KeyStroke key = input.keyStrokeQueue.poll();
                player.processKey(key);
                guard.move();
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
        player = new PlayerController(new Position(50, 50), screen);
        guard = new GuardController(new Position(100, 100));
        screenDrawer.addToDrawableEntities(player);
        screenDrawer.addToDrawableEntities(guard);
    }
}
