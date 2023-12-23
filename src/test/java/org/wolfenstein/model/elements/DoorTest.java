package org.wolfenstein.model.elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoorTest {
    Door testDoor;

    @Test
    void openTest() {
        testDoor = new Door(0, 0, 0);
        assertFalse(testDoor.isOpen());
        testDoor.setOpen(true);
        assertTrue(testDoor.isOpen());
        testDoor.setOpen(false);
        assertFalse(testDoor.isOpen());
    }
}