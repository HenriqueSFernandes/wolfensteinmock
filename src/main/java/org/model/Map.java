package org.model;

public class Map {
    private int[][] map;

    public Map() {

        this.map = new int[][]{
                {1,1,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,1},
                {1,0,0,0,0,0,0,1},
                {1,0,0,0,1,1,1,1},
                {1,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,1,1},
        };
    }

    public int getWidth(){
        return map[0].length;
    }
    public int getHeight(){
        return map.length;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public int getValue(int x, int y){
        return map[y][x];
    }
}
