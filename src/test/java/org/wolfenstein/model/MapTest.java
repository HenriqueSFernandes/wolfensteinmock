package org.wolfenstein.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapTest {
    MapLoader mapLoader;
    Map map;
    Position playerStart;
    Position nextRoom;
    List<Position> doorPos;
    List<Position> guardPos;

    @BeforeEach
    void createMap() throws IOException {
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
    }

    @Test
    void playerStartPositionTest() {
        assertEquals(playerStart.getX(), map.playerStartPosition().getX());
        assertEquals(playerStart.getY(), map.playerStartPosition().getY());
    }

    @Test
    void nextRoomPositionTest() {
        assertEquals(nextRoom.getX(), map.nextRoomPosition().getX());
        assertEquals(nextRoom.getY(), map.nextRoomPosition().getY());
    }

    @Test
    void getPositionsForDoorsTest() {
        for (Position d : map.getPositionsForDoors()) {
            doorPos.removeIf(p -> p.getX() == d.getX() && p.getY() == d.getY());
        }
        assertTrue(doorPos.isEmpty());
    }

    @Test
    void getPositionsForGuardsTest() {
        for (Position g : map.getPositionsForGuards()) {
            guardPos.removeIf(p -> p.getX() == g.getX() && p.getY() == g.getY());
        }
        assertTrue(guardPos.isEmpty());
    }
}