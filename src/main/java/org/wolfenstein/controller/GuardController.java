package org.wolfenstein.controller;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Guard;
import org.wolfenstein.model.sound.SoundLoader;

import java.io.IOException;

public class GuardController extends GameController {
    public GuardController(Camera model) {
        super(model);
    }

    private void moveForward(Guard guard) {
        moveGuard(guard.getPosition().moveForward(), guard);
    }

    private void moveBackward(Guard guard) {
        moveGuard(guard.getPosition().moveBackwards(), guard);
    }

    private void moveGuard(Position position, Guard guard) {
        if (getModel().isEmpty(position)) guard.setPosition(position);
    }

    @Override
    public void step(Game game, GUI.GUIAction action, long time) throws IOException {
        for (Guard guard : getModel().getGuardList()) {
            if (guard.getHealth() > 0) {
                boolean t = guard.tick();
                SoundLoader s = SoundLoader.getInstance();
                double distance = Math.sqrt((getModel().getPlayer().getPosition().getX() - guard.getPosition().getX()) * (getModel().getPlayer().getPosition().getX() - guard.getPosition().getX()) + (getModel().getPlayer().getPosition().getY() - guard.getPosition().getY()) * (getModel().getPlayer().getPosition().getY() - guard.getPosition().getY()));
                if (!guard.isAggro()) {
                    if (distance < 72 && !guard.getPosition().checkWall(getModel().getPlayer().getPosition(), getModel().getMap()))
                        guard.setAggro(true);
                }
                if (guard.isAggro()) {
                    guard.pointTo(getModel().getPlayer().getPosition());
                    if (distance >= 32) {
                        moveForward(guard);
                    } else if (distance < 28) {
                        moveBackward(guard);
                    } else if (distance >= 88 || guard.getPosition().checkWall(getModel().getPlayer().getPosition(), getModel().getMap())) {
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
            double distance;
            double minDist = Integer.MAX_VALUE;
            Guard grd = null;
            for (Guard g : getModel().getGuardList()) {
                distance = Math.sqrt((getModel().getPlayer().getPosition().getX() - g.getPosition().getX()) * (getModel().getPlayer().getPosition().getX() - g.getPosition().getX()) + (getModel().getPlayer().getPosition().getY() - g.getPosition().getY()) * (getModel().getPlayer().getPosition().getY() - g.getPosition().getY()));
                if (!getModel().getPlayer().getPosition().checkWall(g.getPosition(), getModel().getMap())) {
                    /*double m1 = Math.tan(Math.toRadians((getModel().getPlayer().getPosition().getAngle() - 15 + 360) % 360));
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
                    }*/
                    if ((getModel().getPlayer().getPosition().getAngle() >= (g.getPosition().getAngle() - 180 + 360) % 360 - 15) && (getModel().getPlayer().getPosition().getAngle() <= (g.getPosition().getAngle() - 180 + 360) % 360 + 15)) {
                        if (distance < minDist) {
                            minDist = distance;
                            grd = g;
                        }
                    }
                }
            }
            if (grd != null) grd.takeShot();
        }
        SoundLoader soundLoader = SoundLoader.getInstance();
        for (Guard g : getModel().getGuardList()){
            if (g.getHealth() <= 0){
                soundLoader.getSound(1).play();
            }
        }
        getModel().getGuardList().removeIf(guard -> guard.getHealth() <= 0);
    }
}
