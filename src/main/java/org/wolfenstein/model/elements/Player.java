package org.wolfenstein.model.elements;

import org.wolfenstein.model.image.ImageLoader;

public class Player extends Element {

    private static Player player;
    private static int health = 10;
    private static int maxHealth = health;
    private static int ammo = 30;
    private static int maxAmmo = ammo;

    private Player(int x, int y, double angle) {
        super(x, y, angle);
    }

    public static Player getInstance() {
        if (player == null) player = new Player(10, 10, 0);
        return player;
    }

    public static int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int m) {
        maxAmmo = Math.max(1, m);
        ammo = Math.min(ammo, maxAmmo);
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

    public int getAmmo() {
        return ammo;
    }

    public void changeAmmo(int d) {
        ammo = Math.max(0, Math.min(ammo + d, maxAmmo));
    }
}