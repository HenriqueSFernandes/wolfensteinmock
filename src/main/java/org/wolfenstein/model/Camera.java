package org.wolfenstein.model;

import org.wolfenstein.model.elements.Door;
import org.wolfenstein.model.elements.Guard;
import org.wolfenstein.model.elements.Player;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Vector;

public class Camera {
    private static Camera camera;
    private final Player player;
    private List<Guard> guardList;
    private Vector<Door> doors;
    private final Map map;

    private Camera() throws IOException {
        this.player = Player.getInstance();

        this.map = new Map();
        this.guardList = createGuardList();
        this.doors = createDoors();
        player.setPosition(map.playerStartPosition());
    }
    private Camera(Map map, Player player) {
        this.map = map;
        this.player = player;
    }
    public static Camera createCamera() throws IOException {
        if (camera == null) camera = new Camera();
        return camera;
    }
    public static Camera createCamera(Map map, Player player) {
        // This function is only used for testing purposes
        if (camera == null) camera = new Camera(map, player);
        return camera;
    }
    public Player getPlayer() {
        return player;
    }
    public Map getMap() {
        return map;
    }
    public boolean isEmpty(Position position) {
        return getMap().getXY((int) position.getX() / map.getCellsize(), (int) position.getY() / map.getCellsize()) != 1;
    }
    public List<Guard> getGuardList() { return guardList; }

    private List<Guard> createGuardList() {
        guardList = new ArrayList<>();
        guardList.add(new Guard(180, 100, 0));
        guardList.add(new Guard(50, 150, 270));
        guardList.add(new Guard(60, 90, 0));
        return guardList;
    }
    public Vector<Door> getDoors() { return doors; }

    private Vector<Door> createDoors() {
        doors = new Vector<>();
        Vector<Position> doorPos = getMap().getPositionsForDoors();
        for (Position p : doorPos) {
            doors.add(new Door((int) p.getX(), (int) p.getY(), 0));
        }
        return doors;
    }
}
