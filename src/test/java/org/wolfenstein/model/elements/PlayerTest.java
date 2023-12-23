package org.wolfenstein.model.elements;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.wolfenstein.model.elements.Player.getInstance;

public class PlayerTest {
    Player testPlayer;
    @Test
    void createPlayerTest() {
        assertEquals(null, testPlayer);
        testPlayer = getInstance();
        // verify player's default position: point (10, 10) with angle 0
        assertEquals(10, testPlayer.getPosition().getX());
        assertEquals(10, testPlayer.getPosition().getY());
        assertEquals(0, testPlayer.getPosition().getAngle());
        // verify player's singleton status
        testPlayer.setPosition(new Position(20, 20, 0));
        Player otherPlayer = getInstance();
        assertEquals(testPlayer, otherPlayer);
    }
    @Property
    void changeHealthTest(@ForAll @Positive int i) {
        testPlayer = getInstance();
        int testHealth = testPlayer.getHealth();
        testPlayer.changeHealth(-i);
        if (i >= testPlayer.getMaxHealth())
            assertEquals(0, testPlayer.getHealth());
        else
            assertEquals(testHealth - i, testPlayer.getHealth());
    }
}