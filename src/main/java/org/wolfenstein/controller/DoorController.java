package org.wolfenstein.controller;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.elements.Door;

import java.io.IOException;
import java.util.Vector;

public class DoorController extends GameController {
    public DoorController(Camera model) {
        super(model);
    }

    @Override
    public void step(Game game, GUI.GUIAction action, long time) throws IOException {
        Vector<Door> doors = getModel().getDoors();
        if (action == GUI.GUIAction.SELECT) {
            //interact action
            for (Door d : doors) {
                if (Math.sqrt((getModel().getPlayer().getPosition().getX()-d.getPosition().getX())*(getModel().getPlayer().getPosition().getX()-d.getPosition().getX()) + (getModel().getPlayer().getPosition().getY()-d.getPosition().getY())*(getModel().getPlayer().getPosition().getY()-d.getPosition().getY())) <= 11.9) d.setOpen(true);
                System.out.println(Math.sqrt((getModel().getPlayer().getPosition().getX()-d.getPosition().getX())*(getModel().getPlayer().getPosition().getX()-d.getPosition().getX()) + (getModel().getPlayer().getPosition().getY()-d.getPosition().getY())*(getModel().getPlayer().getPosition().getY()-d.getPosition().getY())));
            }
            for (Door d : getModel().getDoors()) {
                System.out.println(d.getPosition().getX());
                System.out.println(d.getPosition().getY());
                System.out.println(d.isVertical());
                System.out.println(d.isOpen());
                System.out.println("------------");
            }
        }
        for (Door d : doors) {
            if (Math.sqrt(Math.pow(getModel().getPlayer().getPosition().getX()-d.getPosition().getX(), 2) + Math.pow(getModel().getPlayer().getPosition().getY()-d.getPosition().getY(), 2)) > 11.9 && d.isOpen()) d.setOpen(false);
        }
    }
}


