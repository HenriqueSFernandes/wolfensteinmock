package org.wolfenstein.controller;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Menu;
import org.wolfenstein.state.MenuState;

import java.io.IOException;

public class CameraController extends GameController {
    private final PlayerController playerController;
    public CameraController(Camera model) {
        super(model);
        playerController = new PlayerController(model);
    }
    @Override
    public void step(Game game, GUI.GUIAction action, long time) throws IOException {
        if (action == GUI.GUIAction.QUIT || playerController.getModel().getPlayer().getHealth() <= 0)
            game.setState(new MenuState(new Menu()));
        else {
            playerController.step(game, action, time);
        }
    }
}
