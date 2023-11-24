package org.wolfenstein.model.elements;

import org.wolfenstein.model.Position;

public class Element {
    private Position position;

    public Element(int x, int y, double angle) {
        position = new Position(x, y, angle);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
