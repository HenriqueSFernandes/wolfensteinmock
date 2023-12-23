package org.wolfenstein.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapLoaderTest {
    MapLoader mapLoader;
    List<List<Integer>> map;

    @BeforeEach
    void createMapLoader() throws IOException {
        mapLoader = MapLoader.createMapLoader();
        mapLoader.importMapFile("src/main/resources/testFiles/testMaps1.txt");
        map = mapLoader.getNextMap();
    }

    @Test
    void importMapFIleTest() throws IOException {
        Vector<Vector<Integer>> testMap = new Vector<>();
        Vector<Integer> line = new Vector<>();
        line.add(0);
        testMap.add(line);

        assertEquals(testMap, map);
    }

    @Test
    void getNextMapTest() throws IOException {
        List<List<Integer>> testMap = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Vector<Integer>line = new Vector<>();
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) line.add(0);
                else line.add(1);
            }
            testMap.add(line);
        }

        map = mapLoader.getNextMap();
        assertEquals(testMap, map);
    }
}