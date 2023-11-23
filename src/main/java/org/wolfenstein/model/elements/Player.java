package org.wolfenstein.model.elements;

import org.wolfenstein.model.Position;

public class Player extends Element {

    private static Player player;
    private static int health;
    private Player(int x, int y, double angle) {
        super(x, y, angle);
        health = 10;
    }
    public static Player createPlayer() {
        if (player == null)
            player = new Player(10, 10, 0);
        return player;
    }
    public void decreaseHealth(int decrease) {
        health -= decrease;
    }
    public void increaseHealth(int increase) {
        health += increase;
    }
    public int getHealth() { return health; }
}
