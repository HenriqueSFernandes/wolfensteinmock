package org.wolfenstein.controller;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Guard;

import java.util.Random;

public class GuardController extends GameController {

    int step_counter = 1;
    public GuardController(Camera model) {
        super(model);
    }
    private void moveForward(Guard guard) { moveGuard(guard.getPosition().moveForward(), guard); }
    private void rotateClockwise(Guard guard) { moveGuard(guard.getPosition().rotateClockwise(), guard); }
    private void rotateAntiClockwise(Guard guard) { moveGuard(guard.getPosition().rotateAntiClockwise(), guard); }
    private void moveBackward(Guard guard) { moveGuard(guard.getPosition().moveBackwards(), guard); }
    private void moveGuard(Position position, Guard guard) {
        if (getModel().isEmpty(position)) guard.setPosition(position);
    }


    @Override
    public void step(Game game, GUI.GUIAction action, long time) {
        for (Guard guard : getModel().getGuardList()) {
            if (step_counter == 0 && guard.getPosition().getAngle() < 180) rotateAntiClockwise(guard);
            else if (step_counter == 10 && guard.getPosition().getAngle() > 0) rotateClockwise(guard);
            else {
                moveForward(guard);
                if (guard.getPosition().getAngle() == 180) step_counter++;
                else step_counter--;
            }
        }
    }
}
