package org.wolfenstein.model.elements;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Guard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GuardTest {
    Guard testGuard;
    @Test
    void takeShotTest() {
        testGuard = new Guard(10, 10, 0);
        int testHealth = testGuard.getHealth();
        assertEquals(false, testGuard.isAggro());
        testGuard.takeShot();
        assertEquals(testHealth - 1, testGuard.getHealth());
        assertEquals(true, testGuard.isAggro());
        testGuard.takeShot();
        assertEquals(testHealth - 2, testGuard.getHealth());
        assertEquals(true, testGuard.isAggro());
    }
    @Provide
    Arbitrary<Position> notOrigin() {
        return Arbitraries.forType(Position.class).filter(p -> p.getX() != 10 || p.getY() != 10);
    }
    @Property
    void aimTest(@ForAll("notOrigin") Position p) {
        testGuard = new Guard(10, 10, 0);
        testGuard.pointTo(p);
        Position testPos = testGuard.getPosition();
        assertTrue(Math.abs(testPos.viewAngle(p)) < 15 || testPos.viewAngle(p) == Integer.MAX_VALUE);
    }
    @Property
    void shootFrequencyTest(@ForAll @IntRange(min = 1, max = 1000) int t) {
        testGuard = new Guard(10, 10, 0);
        int frameNum = t;
        int n = 0;
        while (frameNum != 0) {
            if (testGuard.tick()) n++;
            frameNum--;
        }
        assertEquals(t / 60, n);
    }
}