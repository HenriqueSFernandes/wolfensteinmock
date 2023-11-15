package org.controller;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import org.model.Player;
import org.model.Position;
import org.view.LanternaScreen;

import java.io.IOException;

public class PlayerController extends Player {

    LanternaScreen screen;
    public PlayerController(Position position, LanternaScreen screen) {
        super(position);
        this.screen = screen;
    }

    public void processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
            screen.stopScreen();
        } else {
            switch (key.getKeyType()) {
                case ArrowLeft -> this.rotateAntiClockwise();
                case ArrowRight -> this.rotateClockwise();
                case ArrowUp -> this.moveForward();
                case ArrowDown -> this.moveBackwards();
            }
        }
    }
}
