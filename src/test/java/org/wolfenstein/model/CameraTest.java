package org.wolfenstein.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.wolfenstein.model.elements.Door;
import org.wolfenstein.model.elements.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CameraTest {
    MapLoader mapLoader;
    Map map;
    Position playerStart;
    Position nextRoom;
    List<Position> doorPos;
    List<Position> guardPos;
    Camera testCamera;
    Player player;

    @BeforeEach
    void createCamera() throws IOException {
        mapLoader = MapLoader.createMapLoader();
        mapLoader.importMapFile("src/main/resources/testFiles/testMaps2.txt");
        map = new Map(mapLoader);

        playerStart = new Position(20, 20);
        nextRoom = new Position(84, 52);
        doorPos = new ArrayList<>();
        doorPos.add(new Position(36, 20));
        doorPos.add(new Position(20, 36));
        doorPos.add(new Position(68, 36));
        doorPos.add(new Position(52, 52));
        guardPos = new ArrayList<>();
        guardPos.add(new Position(52, 20));
        guardPos.add(new Position(76, 20));
        guardPos.add(new Position(28, 52));

        player = Mockito.mock(Player.class);

        testCamera = Camera.createCamera(map, player);
    }

    @Test
    void createGuardListTest() {
        assertEquals(guardPos.size(), testCamera.getGuardList().size());
    }

    @Test
    void createDoorsTest() {
        assertEquals(doorPos.size(), testCamera.getDoors().size());
    }

    @Test
    void IsEmptyTest() {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (map.getXY(x, y) == 0) {
                    assertTrue(testCamera.isEmpty(new Position(x * 8 + 4, y * 8 + 4)));
                } else if (map.getXY(x, y) == 1) {
                    assertFalse(testCamera.isEmpty(new Position(x * 8 + 4, y * 8 + 4)));
                } else if (map.getXY(x, y) == 4) {
                    Door d = testCamera.returnDoorAt(x * 8 + 4, y * 8 + 4);
                    assertFalse(testCamera.isEmpty(new Position(x * 8 + 4, y * 8 + 4)));
                    d.setOpen(true);
                    assertTrue(testCamera.isEmpty(new Position(x * 8 + 4, y * 8 + 4)));
                }
            }
        }
    }
}