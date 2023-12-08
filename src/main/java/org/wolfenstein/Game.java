package org.wolfenstein;

import org.wolfenstein.GUI.LanternaGUI;
import org.wolfenstein.model.Menu;
import org.wolfenstein.state.GameState;
import org.wolfenstein.state.MenuState;
import org.wolfenstein.state.State;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private final LanternaGUI gui;
    private State state;
    public Game() throws IOException, URISyntaxException, FontFormatException {
        gui = new LanternaGUI(240*2, 240);
        state = new MenuState(new Menu());
    }

    public static void main(String[] args) throws IOException, URISyntaxException, FontFormatException {
        Game game = new Game();
        game.start();
    }

    private void start() throws IOException {
        int FPS = 120;
        while (this.state != null) {
            if (state.getClass() == MenuState.class) FPS = 5;
            else{
                FPS = 120;
            }
            int frameTime = 1000 / FPS;
            long startTime = System.currentTimeMillis();

            state.step(gui, this, startTime);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;

            try {
                if (sleepTime > 0) Thread.sleep(sleepTime);
            } catch (InterruptedException e) {}
        }

        gui.stopScreen();
    }

    public void setState(State state) {
        this.state = state;
    }
}
