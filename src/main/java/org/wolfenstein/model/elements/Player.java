package org.wolfenstein.model.elements;

import org.wolfenstein.model.image.ImageLoader;

public class Player extends Element {

    private static Player player;
    private static int health = 10;
    private static int maxHealth = health;

    private Player(int x, int y, double angle) {
        super(x, y, angle);
    }

    public static Player getInstance() {
        if (player == null) player = new Player(10, 10, 0);
        return player;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int m) {
        maxHealth = Math.max(1, m);
        health = Math.min(health, maxHealth);
    }

    public void changeHealth(int d) {
        health = Math.max(0, Math.min(health + d, maxHealth));
        ImageLoader imageLoader = ImageLoader.getInstance();
        for (int i = 0; i < maxHealth; i++) {
            imageLoader.getImage(i).setActive(i < health);
        }
    }

    public int getHealth() {
        return health;
    }

}