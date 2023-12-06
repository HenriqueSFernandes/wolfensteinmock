package org.wolfenstein.model.elements;

import static java.lang.Math.min;

public class Guard extends Element {
    private int health;
    private int maxHealth;
    private boolean isAggro;
    public Guard(int x, int y, double angle) {
        super(x, y, angle);
        maxHealth = 20; // temp value
        health = maxHealth;
        this.isAggro = false;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        if (health < 0) this.health = 0;
        else this.health = min(maxHealth, health);
    }
    public void setMaxHealth(int i) {
        if (i >= 1) {
            maxHealth = i;
            if (health > maxHealth) health = maxHealth;
        }
    }
    public int getMaxHealth() { return maxHealth; }

    public boolean isAggro() {
        return isAggro;
    }

    public void setAggro(boolean aggro) {
        isAggro = aggro;
    }
}
