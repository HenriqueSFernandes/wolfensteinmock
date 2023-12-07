package org.wolfenstein.controller;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Map;
import org.wolfenstein.model.Position;

import java.io.IOException;

public class PlayerController extends GameController {
    public PlayerController(Camera model) {
        super(model);
    }
    public void moveForward() { movePlayer(getModel().getPlayer().getPosition().moveForward()); }
    public void rotateClockwise() { movePlayer(getModel().getPlayer().getPosition().rotateClockwise()); }
    public void rotateAntiClockwise() { movePlayer(getModel().getPlayer().getPosition().rotateAntiClockwise()); }
    public void moveBackward() { movePlayer(getModel().getPlayer().getPosition().moveBackwards());}
    public void movePlayer(Position position) {
        if (getModel().isEmpty(position)) getModel().getPlayer().setPosition(position);
    }
    @Override
    public void step(Game game, GUI.GUIAction action, long time) throws IOException {
        if (action == GUI.GUIAction.FRONT) moveForward();
        if (action == GUI.GUIAction.RIGHT) rotateClockwise();
        if (action == GUI.GUIAction.BACK) moveBackward();
        if (action == GUI.GUIAction.LEFT) rotateAntiClockwise();
        if (getModel().getPlayer().getPosition().getX() > getModel().getMap().nextRoomPosition().getX() - getModel().getMap().getCellsize()/2 &&
                getModel().getPlayer().getPosition().getY() > getModel().getMap().nextRoomPosition().getY() - getModel().getMap().getCellsize()/2 &&
                getModel().getPlayer().getPosition().getX() < getModel().getMap().nextRoomPosition().getX() + getModel().getMap().getCellsize()/2 &&
                getModel().getPlayer().getPosition().getY() < getModel().getMap().nextRoomPosition().getY() + getModel().getMap().getCellsize()/2) {
            getModel().getMap().setMap(getModel().getMap().getMapLoader().getNextMap());
            getModel().getPlayer().setPosition(getModel().getMap().playerStartPosition());
        }
        if (action == GUI.GUIAction.SELECT) {
            //interact action
            moveForward();
        }
    }
}
