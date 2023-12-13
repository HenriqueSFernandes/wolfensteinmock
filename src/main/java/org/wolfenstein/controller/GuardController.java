package org.wolfenstein.controller;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Guard;
import org.wolfenstein.model.sound.SoundLoader;

import java.util.List;
import java.util.Random;

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
                boolean t = guard.tick();
                SoundLoader s = SoundLoader.getInstance();
                double distance = Math.sqrt((getModel().getPlayer().getPosition().getX() - guard.getPosition().getX())*(getModel().getPlayer().getPosition().getX() - guard.getPosition().getX()) + (getModel().getPlayer().getPosition().getY() - guard.getPosition().getY())*(getModel().getPlayer().getPosition().getY() - guard.getPosition().getY()));
                /*for (Position position : guard.getPosition().lookForward()) {
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
                if (!guard.isAggro()) moveForward(guard);*/
                if (!guard.isAggro()) {
                    if (distance < 72) guard.setAggro(true);
                }
                if (guard.isAggro()) {
                    guard.pointTo(getModel().getPlayer().getPosition());
                    if (distance >= 32) {
                        moveForward(guard);
                    } else if (distance < 28) {
                        moveBackward(guard);
                    } else if (distance >= 88) {
                        guard.setAggro(false);
                    }
                    if (distance < 32 && t) {
                        s.getSound(0).play();
                        getModel().getPlayer().changeHealth(-1);
                    }
                }
            }
        }
        if (action == GUI.GUIAction.FIRE) {
            for (Guard g : getModel().getGuardList()) {
                double m1 = Math.tan(Math.toRadians((getModel().getPlayer().getPosition().getAngle() - 15 + 360) % 360));
                double m2 = Math.tan(Math.toRadians((getModel().getPlayer().getPosition().getAngle() + 15) % 360));

                if ((getModel().getPlayer().getPosition().getAngle() >= 0 && getModel().getPlayer().getPosition().getAngle() <= 70) ||
                        getModel().getPlayer().getPosition().getAngle() >= 290 && getModel().getPlayer().getPosition().getAngle() <= 350) {
                    if (-g.getPosition().getY() >= m1 * (g.getPosition().getX() - getModel().getPlayer().getPosition().getX()) - getModel().getPlayer().getPosition().getY() &&
                            -g.getPosition().getY() <= m2 * (g.getPosition().getX() - getModel().getPlayer().getPosition().getX()) - getModel().getPlayer().getPosition().getY()) {
                        g.takeShot();
                        break;
                    }
                } else if ((getModel().getPlayer().getPosition().getAngle() >= 80 && getModel().getPlayer().getPosition().getAngle() <= 100) ||
                        getModel().getPlayer().getPosition().getAngle() >= 260 && getModel().getPlayer().getPosition().getAngle() <= 280) {
                    if (-g.getPosition().getY() >= m1 * (g.getPosition().getX() - getModel().getPlayer().getPosition().getX()) - getModel().getPlayer().getPosition().getY() &&
                            -g.getPosition().getY() >= m2 * (g.getPosition().getX() - getModel().getPlayer().getPosition().getX()) - getModel().getPlayer().getPosition().getY()) {
                        g.takeShot();
                        break;
                    }
                } else if (getModel().getPlayer().getPosition().getAngle() >= 110 && getModel().getPlayer().getPosition().getAngle() <= 250) {
                    if (-g.getPosition().getY() >= m1 * (-(g.getPosition().getX() - getModel().getPlayer().getPosition().getX())) - getModel().getPlayer().getPosition().getY() &&
                            -g.getPosition().getY() <= m2 * (-(g.getPosition().getX() - getModel().getPlayer().getPosition().getX())) - getModel().getPlayer().getPosition().getY()) {
                        g.takeShot();
                        break;
                    }
                }
            }
        }
        getModel().getGuardList().removeIf(guard -> guard.getHealth() <= 0);
    }
}
