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
        assertEquals(20, guard.getMaxHealth());

        guard.setMaxHealth(r);
        if (r < 1) assertEquals(20, guard.getMaxHealth());
        else assertEquals(r, guard.getMaxHealth());

        guard.setHealth(r);
        if (r <= 0) assertEquals(0, guard.getHealth());
        else if (r >= guard.getMaxHealth()) assertEquals(guard.getMaxHealth(), guard.getHealth());
        else assertEquals(r, guard.getHealth());
    }
}
