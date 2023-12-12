package org.wolfenstein.controller;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Guard;

public class GuardController extends GameController {
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
            if (guard.getHealth() > 0) {
                for (Position position : guard.getPosition().lookForward()) {
                    if (position.getX() < 0 || position.getY() < 0 ||
                            position.getX() > (getModel().getMap().getWidth() * getModel().getMap().getCellsize())
                            || position.getY() > (getModel().getMap().getWidth() * getModel().getMap().getCellsize()))
                        continue;
                    if (!getModel().isEmpty(position) && !guard.isAggro()) {
                        rotateAntiClockwise(guard);
                        break;
                    }
                }
                guard.setAggro(-Position.FOV / 2.0 <= guard.getPosition().viewAngle(getModel().getPlayer().getPosition())
                        && guard.getPosition().viewAngle(getModel().getPlayer().getPosition()) <= Position.FOV / 2.0);
                if (!guard.isAggro()) moveForward(guard);
            }
        }
        getModel().getGuardList().removeIf(guard -> guard.getHealth() <= 0);
    }
}
