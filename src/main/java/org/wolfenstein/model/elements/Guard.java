package org.wolfenstein.model.elements;

import org.wolfenstein.model.Position;

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
    public void pointTo(Position p) {
        if (p.getX() == this.getPosition().getX()) {

            this.setPosition(new Position(this.getPosition().getX(), this.getPosition().getY(), 90));
        } else {
            this.setPosition(new Position(this.getPosition().getX(), this.getPosition().getY(), (((int) Math.toDegrees(Math.atan(-(p.getY() - this.getPosition().getY()) / (p.getX() - this.getPosition().getX())))) / 10) * 10));
        }
        if (Math.sqrt((p.getX() - this.getPosition().getX())*(p.getX() - this.getPosition().getX()) + (p.getY() - this.getPosition().getY())*(p.getY() - this.getPosition().getY())) <
                Math.sqrt((p.getX() - this.getPosition().moveForward().getX())*(p.getX() - this.getPosition().moveForward().getX()) + (p.getY() - this.getPosition().moveForward().getY())*(p.getY() - this.getPosition().moveForward().getY())))
            this.setPosition(new Position(this.getPosition().getX(), this.getPosition().getY(), (this.getPosition().getAngle() + 180) % 360));
    }
}


