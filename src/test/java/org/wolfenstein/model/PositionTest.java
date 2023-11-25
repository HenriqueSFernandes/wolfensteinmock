package org.wolfenstein.model;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {
    private Position position;

    @Property
    void moveRightTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 2) int x) {
        position = new Position(x, 0, 0);
        assertEquals(x + 2, position.moveForward().getX());
        assertEquals(x - 2, position.moveBackwards().getX());
    }

    @Property
    void moveLeftTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 2) int x) {
        position = new Position(x, 0, 180);
        assertEquals(x - 2, position.moveForward().getX());
        assertEquals(x + 2, position.moveBackwards().getX());
    }

    @Property
    void moveDownTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 2) int y) {
        position = new Position(0, y, 270);
        assertEquals(y + 2, position.moveForward().getY());
        assertEquals(y - 2, position.moveBackwards().getY());
    }

    @Property
    void moveUpTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 2) int y) {
        position = new Position(0, y, 90);
        assertEquals(y - 2, position.moveForward().getY());
        assertEquals(y + 2, position.moveBackwards().getY());
    }
}
