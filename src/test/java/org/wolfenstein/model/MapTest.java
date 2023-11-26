package org.wolfenstein.model;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class MapTest {
    int[][] m = {{1,1,1,1,1,1,1,1},
                 {1,0,0,0,0,0,0,1},
                 {1,0,0,0,0,0,0,1},
                 {1,0,0,0,0,0,0,1},
                 {1,1,1,1,1,1,1,1}};
    Map testMap = new Map(m);
    private final int testHeight = 5;
    private final int testWidth = 8;

    //GetHeight/WidthTest
    @Property
    void getXYTest(@ForAll @IntRange(min = 0, max = testHeight - 1) int y,
                   @ForAll @IntRange(min = 0, max = testWidth - 1) int x) {
        assertEquals(testMap.getXY(x, y), m[y][x]);
    }

    @Test
    void getHeightTest() {
        assertEquals(testHeight, testMap.getHeight());
    }

    @Test
    void getWidthTest() {
        assertEquals(testWidth, testMap.getWidth());
    }
}
