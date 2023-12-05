package org.wolfenstein.model;


import java.io.IOException;
import java.util.Vector;

public class Map {
    private Vector<Vector<Integer>> map = new Vector<>();
    private int width;
    private int height;
    private final int cellsize = 8;
    private MapLoader mapLoader;
    public void setMap(Vector<Vector<Integer>> newMap) {
        this.map = newMap;
        this.height = newMap.size();
        this.width = newMap.get(0).size();
    }
    public Vector<Vector<Integer>> getMap() { return map; }
    public int getXY(int x, int y) { return map.get(y).get(x); }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public int getCellsize() {
        return cellsize;
    }
    public Map() throws IOException {
        mapLoader = MapLoader.createMapLoader();
        mapLoader.importMapFile();
        setMap(mapLoader.getNextMap());
        //setMap(mapLoader.getNextMap());
    }
    public Map(Vector<Vector<Integer>> newMap) {
        //Construtor só para testes
        setMap(newMap);
    }
    public Position playerStartPosition() {
        Position startPos = new Position(-1, -1, 0);
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x) != 3) continue;
                startPos = new Position(8.0 * x + 4, 8.0 * y + 4, 0.0);
                break;
            }
        }
        return startPos;
    }
    public MapLoader getMapLoader() {
        return mapLoader;
    }
}
