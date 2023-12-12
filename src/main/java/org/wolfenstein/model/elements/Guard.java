package org.wolfenstein.model.elements;

import static java.lang.Math.min;

public class Guard extends Element {
    private int health;
    private boolean isAggro;
    private boolean alive;
    public Guard(int x, int y, double angle) {
        super(x, y, angle);
        health = 3;
        this.isAggro = false;
    }
    public int getHealth() {
        return health;
    }
    public boolean isAggro() {
        return isAggro;
    }
    public void setAggro(boolean aggro) {
        isAggro = aggro;
    }
    public void takeShot() {
        health--;
        if (!isAggro) isAggro = true;
    }
}
