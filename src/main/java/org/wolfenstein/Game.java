package org.wolfenstein;

import org.wolfenstein.GUI.LanternaGUI;
import org.wolfenstein.model.Menu;
import org.wolfenstein.state.MenuState;
import org.wolfenstein.state.State;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private final LanternaGUI gui;
    private State state;

    public Game() throws IOException, URISyntaxException, FontFormatException {
        gui = new LanternaGUI(240 * 2, 240);
        state = new MenuState(new Menu());
    }

    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException {
        try {
            Game game = new Game();
            game.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void start() throws IOException, InterruptedException {
        int FPS = 120;
        while (this.state != null) {
            if (state.getClass() == MenuState.class) FPS = 60;
            else {
                FPS = 120;
            }
            int frameTime = 1000 / FPS;
            long startTime = System.currentTimeMillis();

            state.step(gui, this, startTime);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;
            if (sleepTime > 0) Thread.sleep(sleepTime);
        }

        gui.stopScreen();
    }

    public void setState(State state) {
        this.state = state;
    }
}
