package org.wolfenstein;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest {
    @Test
    public void gameTest() {
        assertDoesNotThrow(() -> { Game.main(null); });
    }
}
