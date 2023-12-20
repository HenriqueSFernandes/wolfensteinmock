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
    private List<Door> doors;
    private static final int maxGuardNumber = 30;
    private final Map map;

    private Camera() throws IOException {
        this.player = Player.getInstance();
        this.map = new Map();
        this.doors = new ArrayList<>();
        this.guardList = new ArrayList<>();
        createDoors();
        createGuardList();
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
        return (getMap().getXY((int) position.getX() / map.getCellsize(), (int) position.getY() / map.getCellsize()) != 1) &&
                !(getMap().getXY((int) position.getX() / map.getCellsize(), (int) position.getY() / map.getCellsize()) == 4 && !returnDoorAt((int) position.getX(), (int) position.getY()).isOpen());
    }
    public List<Guard> getGuardList() { return guardList; }
    public List<Guard> createGuardList() {
        guardList = new ArrayList<>();
        List<Position> guardPos = getMap().getPositionsForGuards();
        for (Position p : guardPos) {
            Guard g = new Guard((int) p.getX(), (int) p.getY(), 0);
            guardList.add(g);
        }
        return guardList;
    }
    public List<Door> getDoors() { return doors; }
    public List<Door> createDoors() {
        doors = new ArrayList<>();
        List<Position> doorPos = getMap().getPositionsForDoors();
        for (Position p : doorPos) {
            Door d = new Door((int) p.getX(), (int) p.getY(), 0);
            if (map.getXY((int) ((p.getX() - 4) / 8) + 1, (int) (p.getY() - 4) / 8) == 1) d.setVertical(true);
            doors.add(d);
        }
        return doors;
    }
    public Door returnDoorAt(int x, int y) {
        for (Door d : doors) {
            if (d.getPosition().getX() - 4 <= x && d.getPosition().getY() - 4 <= y && d.getPosition().getX() + 4 >= x && d.getPosition().getY() + 4 >= y) return d;
        }
        return null;
    }
    public int getMaxGuardNumber() { return maxGuardNumber; }
}