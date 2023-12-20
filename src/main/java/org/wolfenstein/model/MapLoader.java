package org.wolfenstein.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {
    private static MapLoader mapLoader;
    private static FileInputStream in;
    private static List<List<Integer>> extractedMap = new ArrayList<>();
    private static int c;

    private MapLoader() {
    }

    public static MapLoader createMapLoader() {
        if (mapLoader == null) {
            mapLoader = new MapLoader();
        }
        return mapLoader;
    }

    public void importMapFile() throws IOException {
        in = new FileInputStream("src/main/resources/maps/map.txt");
        c = in.read();
    }

    public List<List<Integer>> getNextMap() throws IOException {
        extractedMap = new ArrayList<>();
        while ((c = in.read()) != 88) {
            List<Integer> line = new ArrayList<>();
            line.add(c - 48);
            while ((c = in.read()) != 10) {
                line.add(c - 48);
            }
            extractedMap.add(line);

        }
        return extractedMap;
    }
}
