package org.wolfenstein.model;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.wolfenstein.model.elements.Guard;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GuardTest {

    @Property
    void HealthTest(@ForAll int r) {
        Guard guard = new Guard(10, 10, 0);
        assertEquals(20, guard.getHealth());
        guard.setMaxHealth(r);
        if (r > 0 && r < 20) {
            assertEquals(r, guard.getHealth());
        } else {
            assertEquals(20, guard.getHealth());
            if (r >= 20) {
                guard.setHealth(Integer.MAX_VALUE);
                assertEquals(r, guard.getHealth());
            }
        }
        guard.setMaxHealth(100);
        guard.setHealth(Integer.MAX_VALUE);
    }
}
