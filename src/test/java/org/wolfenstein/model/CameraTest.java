package org.wolfenstein.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.wolfenstein.model.elements.Player;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CameraTest {
    @Test
    void isEmptyTest() {
        int[][] customMap = {{0, 1}, {1, 0}};
        Map mapMock = new Map();
        mapMock.setMap(customMap);

        Player playerMock = Mockito.mock(Player.class);

        Camera camera = Camera.createCamera(mapMock, playerMock);

        assertTrue(camera.isEmpty(new Position(0, 0)));
        assertTrue(camera.isEmpty(new Position(8, 8)));
        assertFalse(camera.isEmpty(new Position(8, 0)));
        assertFalse(camera.isEmpty(new Position(0, 8)));
    }
}
