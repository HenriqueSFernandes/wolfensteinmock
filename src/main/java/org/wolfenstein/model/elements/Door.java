package org.wolfenstein.model.elements;

public class Door extends Element {
    private boolean open;
    private boolean vertical;
    public Door(int x, int y, double angle) {
        super(x, y, angle);
    }
    public boolean isOpen() {
        return open;
    }

    public boolean isVertical() { return vertical; }
}