package org.wolfenstein.model.elements;

public class Door extends Element {
    private boolean open;

    public Door(int x, int y, double angle) {
        super(x, y, angle);
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean o) {
        open = o;
    }
}