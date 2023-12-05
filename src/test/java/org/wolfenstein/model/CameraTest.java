package org.wolfenstein.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.wolfenstein.model.elements.Player;

import java.io.IOException;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CameraTest {
    @Test
    void isEmptyTest() throws IOException {
        Vector<Vector<Integer>> customMap = new Vector<>();
        Vector<Integer> line = new Vector<>();
        line.add(0);
        line.add(1);
        customMap.add(line);
        line = new Vector<>();
        line.add(1);
        line.add(0);
        customMap.add(line);
        Map mapMock = new Map();
        mapMock.setMap(customMap);

        Player playerMock = Mockito.mock(Player.class);

        Camera camera = Camera.createCamera(mapMock, playerMock);

        assertTrue(camera.isEmpty(new Position(0, 0, 0)));
        assertTrue(camera.isEmpty(new Position(8, 8, 0)));
        assertFalse(camera.isEmpty(new Position(8, 0, 0)));
        assertFalse(camera.isEmpty(new Position(0, 8, 0)));
    }
}
