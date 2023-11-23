package org.wolfenstein.model;

import org.wolfenstein.model.elements.Player;

public class Camera {
    private final Player player;
    private final Map map;
    private static Camera camera;
    private Camera() {
        this.player = Player.createPlayer();
        this.map = new Map();
    }
    public static Camera createCamera() {
        if (camera == null)
            camera = new Camera();
        return camera;
    }
    public Player getPlayer() {
        return player;
    }
    public Map getMap() {
        return map;
    }
    public boolean isEmpty(Position position) {
        return getMap().getXY(position.x / map.getCellsize(), position.y / map.getCellsize()) != 1;
    }
}
