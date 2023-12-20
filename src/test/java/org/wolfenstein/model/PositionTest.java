package org.wolfenstein.model;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Positive;

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

    @Property
    void rotateTest(@ForAll @Positive @IntRange(min = 0, max = 359) int th, @ForAll int r) {
        position = new Position(r, r, th);

        position = position.rotateClockwise();
        assertEquals((th - 10.0 + 360.0) % 360.0, position.getAngle());
        assertEquals(r, position.getX());
        assertEquals(r, position.getY());

        position = position.rotateAntiClockwise();
        assertEquals(th, position.getAngle());
        assertEquals(r, position.getX());
        assertEquals(r, position.getY());
    }

    @Property
    void goAndGoBackTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 2) int x, @ForAll @IntRange(max = Integer.MAX_VALUE - 2) int y) {
        position = new Position(x, y, 0);
        for (int j = 0; j < 4; j++) {
            position = position.moveForward();
            for (int i = 0; i < 9; i++) {
                position = position.rotateClockwise();
            }
        }
        assertEquals(x, position.getX());
        assertEquals(y, position.getY());
    }
}
