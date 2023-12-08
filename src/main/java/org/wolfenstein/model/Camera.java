package org.wolfenstein.model;

import org.wolfenstein.model.elements.Guard;
import org.wolfenstein.model.elements.Player;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Camera {
    private static Camera camera;
    private final Player player;
    private List<Guard> guardList;
    private final Map map;

    private Camera() throws IOException {
        this.player = Player.getInstance();
        this.map = new Map();
        this.guardList = createGuardList();
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
        guardList.add(new Guard(210, 160, 0));
        guardList.add(new Guard(50, 150, 270));
        guardList.add(new Guard(60, 90, 0));
        return guardList;
    }
}
