package org.wolfenstein.model.elements;

import org.wolfenstein.model.image.ImageLoader;

public class Player extends Element {

    private static Player player;
    private static int health;
    private static int maxHealth;
    private static int ammo;
    private static int maxAmmo;

    private Player(int x, int y, double angle) {
        super(x, y, angle);
        health = 10;
        ammo = 30;
        //by default, max values for health and ammo are the same as their default values when player is created
        maxHealth = health;
        maxAmmo = ammo;
    }
    public static Player getInstance() {
        if (player == null) player = new Player(10, 10, 0);
        return player;
    }
    //public void setHealth(int h) { health = h; }
    public static int getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(int m) {
        maxHealth = Math.max(1, m);
        health = Math.min(health, maxHealth);
    }
    public void changeHealth(int d) {
        health = Math.max(0, Math.min(health + d, maxHealth));
    }
    public int getHealth() {
        return health;
    }
    public int getAmmo() {
        return ammo;
    }
    //public void setAmmo(int a) { ammo = a; }
    public static int getMaxAmmo() {
        return maxAmmo;
    }
    public void setMaxAmmo(int m) {
        maxAmmo = Math.max(1, m);
        ammo = Math.min(ammo, maxAmmo);
    }
    public void changeAmmo(int d) {
        ammo = Math.max(0, Math.min(ammo + d, maxAmmo));
    }
}