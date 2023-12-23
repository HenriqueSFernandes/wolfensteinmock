package org.wolfenstein.model.elements;

import org.junit.jupiter.api.Test;
import org.wolfenstein.model.elements.Door;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoorTest {
    Door testDoor;
    @Test
    void openTest() {
        testDoor = new Door(0, 0, 0);
        assertEquals(false, testDoor.isOpen());
        testDoor.setOpen(true);
        assertEquals(true, testDoor.isOpen());
        testDoor.setOpen(false);
        assertEquals(false, testDoor.isOpen());
    }
}