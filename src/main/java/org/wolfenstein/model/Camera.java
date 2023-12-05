package org.wolfenstein.model;

import org.wolfenstein.model.elements.Player;

import java.io.IOException;

public class Camera {
    private static Camera camera;
    private final Player player;
    private final Map map;

    private Camera() throws IOException {
        this.map = new Map();
        this.player = Player.createPlayer();
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
        return getMap().getXY((int) position.x / map.getCellsize(), (int) position.y / map.getCellsize()) != 1;
    }
}
