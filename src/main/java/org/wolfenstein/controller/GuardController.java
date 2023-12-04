package org.wolfenstein.controller;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Guard;

import java.util.Random;

public class GuardController extends GameController {
    public GuardController(Camera model) {
        super(model);
    }
    public void moveForward(Guard guard) { moveGuard(guard.getPosition().moveForward(), guard); }
    public void rotateClockwise(Guard guard) { moveGuard(guard.getPosition().rotateClockwise(), guard); }
    public void rotateAntiClockwise(Guard guard) { moveGuard(guard.getPosition().rotateAntiClockwise(), guard); }
    public void moveBackward(Guard guard) { moveGuard(guard.getPosition().moveBackwards(), guard); }
    public void moveGuard(Position position, Guard guard) {
        if (getModel().isEmpty(position)) guard.setPosition(position);
    }
    @Override
    public void step(Game game, GUI.GUIAction action, long time) {
        Random rand = new Random();
        int next;
        for (Guard guard : getModel().getGuardList()) {
            next = rand.nextInt(4);
            switch (next) {
                case 0:
                    moveForward(guard);
                    break;
                case 1:
                    moveBackward(guard);
                    break;
                case 2:
                    rotateClockwise(guard);
                    break;
                case 3:
                    rotateAntiClockwise(guard);
                    break;
            }
        }
    }
}
