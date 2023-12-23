package org.wolfenstein.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private final int cellsize = 8;
    private List<List<Integer>> map = new ArrayList<>();
    private int width;
    private int height;
    private MapLoader mapLoader;

    public Map() throws IOException {
        mapLoader = MapLoader.createMapLoader();
        mapLoader.importMapFile();
        setMap(mapLoader.getNextMap());
    }

    public Map(List<List<Integer>> newMap) {
        setMap(newMap);
    }

    public Map(MapLoader m) throws IOException {
        mapLoader = m;
        setMap(mapLoader.getNextMap());
    }

    public List<List<Integer>> getMap() {
        return map;
    }

    public void setMap(List<List<Integer>> newMap) {
        this.map = newMap;
        this.height = newMap.size();
        this.width = newMap.get(0).size();
    }

    public int getXY(int x, int y) {
        return map.get(y).get(x);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getCellsize() {
        return cellsize;
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

    public Position nextRoomPosition() {
        Position transPos = new Position(-1, -1, 0);
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x) != 2) continue;
                transPos = new Position(8.0 * x + 4, 8.0 * y + 4, 0.0);
                break;
            }
        }
        return transPos;
    }

    public List<Position> getPositionsForDoors() {
        List<Position> res = new ArrayList<>();
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x) != 4) continue;
                Position doorPos = new Position(8.0 * x + 4, 8.0 * y + 4, 0.0);
                res.add(doorPos);
            }
        }
        return res;
    }

    public MapLoader getMapLoader() {
        return mapLoader;
    }

    public List<Position> getPositionsForGuards() {
        List<Position> res = new ArrayList<>();
        Position doorPos = new Position(-1, -1, 0);
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x) != 5) continue;
                doorPos = new Position(8.0 * x + 4, 8.0 * y + 4, 0.0);
                res.add(doorPos);
            }
        }
        return res;
    }
}
