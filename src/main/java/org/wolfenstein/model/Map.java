package org.wolfenstein.model;


import java.io.IOException;
import java.util.Vector;

public class Map {
    private Vector<Vector<Integer>> map = new Vector<>();
    private int width;
    private int height;
    private final int cellsize = 8;
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
        MapLoader mapLoader = MapLoader.createMapLoader();
        mapLoader.importMapFile();
        setMap(mapLoader.getNextMap());
        //setMap(mapLoader.getNextMap());
    }
    public Map(Vector<Vector<Integer>> newMap) {
        //Construtor só para testes
        setMap(newMap);
    }
}
