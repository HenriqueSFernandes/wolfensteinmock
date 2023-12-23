package org.wolfenstein.model.elements;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.image.ImageLoader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.wolfenstein.model.elements.Player.getInstance;

public class PlayerTest {
    Player testPlayer;

    @Test
    void createPlayerTest() {
        assertNull(testPlayer);
        double x = Player.getInstance().getPosition().getX();
        double y = Player.getInstance().getPosition().getY();
        testPlayer = getInstance();
        assertEquals(x, testPlayer.getPosition().getX());
        assertEquals(y, testPlayer.getPosition().getY());
        assertEquals(0, testPlayer.getPosition().getAngle());
        testPlayer.setPosition(new Position(20, 20, 0));
        Player otherPlayer = getInstance();
        assertEquals(testPlayer, otherPlayer);
    }

    @Property
    void changeHealthTest(@ForAll @Positive int i) throws IOException {
        testPlayer = getInstance();
        ImageLoader imageLoader = ImageLoader.getInstance();
        for (int j = 0; j < testPlayer.getMaxHealth(); j++)
            imageLoader.importImage("image_test.png", new Position(0, 0));
        int testHealth = testPlayer.getHealth();
        testPlayer.changeHealth(-i);
        if (i >= testPlayer.getHealth()) assertEquals(0, testPlayer.getHealth());
        else assertEquals(testHealth - i, testPlayer.getHealth());
    }
}