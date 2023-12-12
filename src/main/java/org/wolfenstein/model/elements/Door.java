package org.wolfenstein.model.elements;

import org.wolfenstein.model.Position;

public class Door extends Element {
    private boolean open;
    private boolean vertical;
    public Door(int x, int y, double angle) {
        super(x, y, angle);
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean o) { open = o; }
    public boolean isVertical() { return vertical; }
    public void setVertical(boolean v) { vertical = v; }
}