package org.wolfenstein.model;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.wolfenstein.model.elements.Player;

import javax.xml.catalog.CatalogManager;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    private Position position;

    @Property
    void moveRightTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 2) int x) {
        position = new Position(x, 0, 0);
        assertEquals(x + 2, position.moveForward().getX());
        assertEquals(x - 2, position.moveBackwards().getX());
    }

    @Property
    void moveLeftTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 2) int x) {
        position = new Position(x, 0, 180);
        assertEquals(x - 2, position.moveForward().getX());
        assertEquals(x + 2, position.moveBackwards().getX());
    }

    @Property
    void moveDownTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 2) int y) {
        position = new Position(0, y, 270);
        assertEquals(y + 2, position.moveForward().getY());
        assertEquals(y - 2, position.moveBackwards().getY());
    }

    @Property
    void moveUpTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 2) int y) {
        position = new Position(0, y, 90);
        assertEquals(y - 2, position.moveForward().getY());
        assertEquals(y + 2, position.moveBackwards().getY());
    }

    @Property
    void rotateTest(@ForAll @Positive @IntRange(min = 0, max = 359) int th,
                    @ForAll int r) {
        position = new Position(r, r, th);

        position = position.rotateClockwise();
        assertEquals((th - 10.0 + 360.0) % 360.0, position.getAngle());
        assertEquals(r, position.getX());
        assertEquals(r, position.getY());

        position = position.rotateAntiClockwise();
        assertEquals(th, position.getAngle());
        assertEquals(r, position.getX());
        assertEquals(r, position.getY());
    }

    @Property
    void createLineTest(@ForAll @IntRange(min = 0, max = 360) int th) throws IOException {
        MapLoader mapLoader = MapLoader.createMapLoader();
        mapLoader.importMapFile("src/main/resources/testFiles/testMaps3.txt");
        Map map = new Map(mapLoader);
        Position testPos = map.playerStartPosition();
        double avgX = 0, avgY = 0;

        List<Position> testLine = testPos.createLine(th, map);

        for (Position p : testLine) {
            // check if line does not pass through walls
            assertEquals(false, map.getXY((int) p.getX() / 8,  (int) p.getY() / 8) == 1);
            avgX += (p.getX() - testPos.getX());
            avgY -= (p.getY() - testPos.getY());
            // check if any of the points of the line are behind the player -> line exists only in the same quadrant as the direction of the player
            if (th > 0 && th < 90) {
                assertFalse(p.getY() - testPos.getY() > 0 || p.getX() - testPos.getX() < 0);
            } else if (th > 90 && th < 180) {
                assertFalse(p.getY() - testPos.getY() > 0 || p.getX() - testPos.getX() > 0);
            } else if (th > 180 && th < 270) {
                assertFalse(p.getY() - testPos.getY() < 0 || p.getX() - testPos.getX() > 0);
            } else if (th > 270 && th < 360) {
                assertFalse(p.getY() - testPos.getY() < 0 || p.getX() - testPos.getX() < 0);
            } else if (th == 0 || th == 360) {
                assertFalse(p.getY() - testPos.getY() > 0);
            } else if (th == 90) {
                assertFalse(p.getX() - testPos.getX() > 0);
            } else if (th == 180) {
                assertFalse(p.getY() - testPos.getY() < 0);
            } else if (th == 270) {
                assertFalse(p.getX() - testPos.getX() < 0);
            }
        }

        // check if the points follow a straight line - Linear Regression
        double sumN = 0, sumD = 0;
        for (Position p : testLine) {
            sumN += ((p.getX() - testPos.getX()) - avgX) * ((p.getY() - testPos.getY()) - avgY);
            sumD += ((p.getX() - testPos.getX()) - avgX) * ((p.getX() - testPos.getX()) - avgX);
        }
        double alpha = Math.toDegrees(Math.atan(sumN / sumD));
        if (sumD == 0) alpha = 90;
        if (alpha < 0) alpha += 180;
        // max deviance of 10º
        assertTrue((Math.max(0, (th - 10) % 180) <= alpha && Math.min((th + 10 % 180), 180) >= alpha) || (Math.max(0, (th - 10) % 180) <= alpha + 180 && Math.min((th + 10 % 180), 180) >= alpha));
    }
    @Property
    // TODO
    void createLineForDoorTest(@ForAll @IntRange(min = 0, max = 360) int th) throws IOException {
        /*
        MapLoader mapLoader = MapLoader.createMapLoader();
        mapLoader.importMapFile("src/main/resources/testFiles/testMaps2.txt");
        Map map = new Map(mapLoader);
        Position testPos = map.playerStartPosition();
        double avgX = 0, avgY = 0;
        Player playerMock = Mockito.mock(Player.class);
        Camera cam = Camera.createCamera(map, playerMock);

        List<Position> testLine = testPos.createLineForDoor(th, map);

        for (Position p : testLine) {
            // check if line does not pass through walls
            assertEquals(false, map.getXY((int) p.getX() / 8, (int) p.getY() / 8) == 1);
            // check if line does not pass through closed doors
            assertEquals(false, map.getXY((int) (p.getX() - 4) / 8, (int) (p.getY() - 4) / 8) == 4);
            avgX += (p.getX() - testPos.getX());
            avgY -= (p.getY() - testPos.getY());
            // check if any of the points of the line are behind the player -> line exists only in the same quadrant as the direction of the player
            if (th > 0 && th < 90) {
                assertFalse(p.getY() - testPos.getY() > 0 || p.getX() - testPos.getX() < 0);
            } else if (th > 90 && th < 180) {
                assertFalse(p.getY() - testPos.getY() > 0 || p.getX() - testPos.getX() > 0);
            } else if (th > 180 && th < 270) {
                assertFalse(p.getY() - testPos.getY() < 0 || p.getX() - testPos.getX() > 0);
            } else if (th > 270 && th < 360) {
                assertFalse(p.getY() - testPos.getY() < 0 || p.getX() - testPos.getX() < 0);
            } else if (th == 0 || th == 360) {
                assertFalse(p.getY() - testPos.getY() > 0);
            } else if (th == 90) {
                assertFalse(p.getX() - testPos.getX() > 0);
            } else if (th == 180) {
                assertFalse(p.getY() - testPos.getY() < 0);
            } else if (th == 270) {
                assertFalse(p.getX() - testPos.getX() < 0);
            }
        }

        // check if the points follow a straight line - Linear Regression
        double sumN = 0, sumD = 0;
        for (Position p : testLine) {
            sumN += ((p.getX() - testPos.getX()) - avgX) * ((p.getY() - testPos.getY()) - avgY);
            sumD += ((p.getX() - testPos.getX()) - avgX) * ((p.getX() - testPos.getX()) - avgX);
        }
        double alpha = Math.toDegrees(Math.atan(sumN / sumD));
        if (sumD == 0) alpha = 90;
        if (alpha < 0) alpha += 180;
        // max deviance of 10º
        assertTrue((Math.max(0, (th - 10) % 180) <= alpha && Math.min((th + 10 % 180), 180) >= alpha) || (Math.max(0, (th - 10) % 180) <= alpha + 180 && Math.min((th + 10 % 180), 180) >= alpha));
        */
    }
    @Provide
    Arbitrary<Position> notOrigin() {
        return Arbitraries.forType(Position.class).filter(p -> p.getX() != 0 || p.getY() != 0);
    }
    @Property
    // TODO
    void viewAngleTest(@ForAll("notOrigin") Position p, @ForAll @IntRange(min = 0, max = 360) int th) {
        Position testPos = new Position(0, 0, th);
        Position testObject = p;
        double alpha = Math.toDegrees(Math.atan2(testObject.getY(), testObject.getX()));
        assertTrue(Math.round(-((alpha + th) % 180)) == Math.round(testPos.viewAngle(testObject)) || Math.round(-((alpha + th) % 180) + 180) == Math.round(testPos.viewAngle(testObject)));
    }

    @Property
    void distanceTest(@ForAll @IntRange(min = -1000, max = 1000) int x, @ForAll @IntRange(min = -1000, max = 1000) int y, @ForAll @IntRange(min = -1000, max = 1000) int x2, @ForAll @IntRange(min = -1000, max = 1000) int y2) {
        Position testPos = new Position(x, y);
        Position testObject = new Position(x2, y2);
        assertEquals(Math.sqrt((x - x2)*(x - x2) + (y - y2)*(y - y2)), testPos.distance(testObject));
    }

    @Property
    // TODO
    void checkWallTest(@ForAll @IntRange(min = 0, max = 64) int x, @ForAll @IntRange(min = 0, max = 64) int y) throws IOException {
        MapLoader mapLoader = MapLoader.createMapLoader();
        mapLoader.importMapFile("src/main/resources/testFiles/testMaps3.txt");
        Map map = new Map(mapLoader);
        Position testPos = map.playerStartPosition();
        Position testObject = new Position(x, y);
        if (x > 39 && y > 7 && x < 63 && y < 63) {
            // there is no wall blocking the spaces in the same room
            assertFalse(testPos.checkWall(testObject, map));
        } else if (x > 7 && y > 7 && x < 31 && y < 63) {
            // there is a wall blocking the spaces in separate rooms
            assertTrue(testPos.checkWall(testObject, map));
        }

    }
}