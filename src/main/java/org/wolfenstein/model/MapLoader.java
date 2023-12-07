package org.wolfenstein.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;

public class MapLoader {
    private static MapLoader mapLoader;
    private static FileInputStream in;
    private static Vector<Vector<Integer>> extractedMap = new Vector<>();
    private static int c;
    private MapLoader() {}
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
    public Vector<Vector<Integer>> getNextMap() throws IOException {
        extractedMap = new Vector<>();
        while ((c = in.read()) != 88) {
            Vector<Integer> line = new Vector<>();
            line.add(c-48);
            while ((c = in.read()) != 10) {
                line.add(c-48);
            }
            extractedMap.add(line);
        }
        return extractedMap;
    }
}
