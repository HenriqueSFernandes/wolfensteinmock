package org.wolfenstein.controller;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Door;

import java.io.IOException;
import java.util.Vector;

public class DoorController extends GameController {
    public DoorController(Camera model) {
        super(model);
    }

    @Override
    public void step(Game game, GUI.GUIAction action, long time) throws IOException {
        if (action == GUI.GUIAction.SELECT) {
            //interact action
            Vector<Door> d = getModel().getDoors();
            for (Door dr : d) {
                System.out.println(dr.getPosition().getX());
                System.out.println(dr.getPosition().getY());
                System.out.println("------------");
            }
        }
    }
}
