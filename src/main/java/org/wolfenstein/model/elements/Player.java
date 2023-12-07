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

    public static int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int i) {
        if (i >= 1) {
            maxHealth = i;
            if (health > maxHealth) health = maxHealth;
        }
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth(int decrease) {
        if (decrease > 0) {
            health -= decrease;
            if (health < 0) health = 0;
        }
        ImageLoader imageLoader = ImageLoader.getInstance();
        for (int i = maxHealth - 1; i >= health; i--){
            imageLoader.getImage(i).setActive(false);
        }
    }

    public void increaseHealth(int increase) {
        if (increase > 0) {
            health += increase;
            if (health > maxHealth) health = maxHealth;
        }
        ImageLoader imageLoader = ImageLoader.getInstance();
        for (int i = 0; i < health; i++){
            imageLoader.getImage(i).setActive(true);
        }
    }

    public int getAmmo() {
        return ammo;
    }

    public void setMaxAmmo(int i) {
        if (i >= 1) {
            maxAmmo = i;
            if (ammo > maxAmmo) ammo = maxAmmo;
        }
    }

    public void decreaseAmmo(int decrease) {
        if (decrease > 0) {
            ammo -= decrease;
            if (ammo < 0) ammo = 0;
        }
    }

    public void increaseAmmo(int increase) {
        if (increase > 0) {
            ammo += increase;
            if (ammo > maxAmmo) ammo = maxAmmo;
        }
    }
}