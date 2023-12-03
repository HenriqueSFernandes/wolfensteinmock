package org.wolfenstein.model;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;
import org.wolfenstein.model.elements.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.wolfenstein.model.elements.Player.createPlayer;

public class PlayerTest {
    Player player;
    @Test
    void createPlayerTest() {
        assertEquals(null, player);
        player = createPlayer();
        //a posição default do player é no ponto (10, 10) com ângulo 0
        assertEquals(10, player.getPosition().getX());
        assertEquals(10, player.getPosition().getY());
        assertEquals(0, player.getPosition().getAngle());
    }

    /*@Property
    void setMaxHealthTest(@ForAll int r) {
        player = createPlayer();
        assertEquals(100, player.getHealth());
        player.setMaxHealth(r);
        if (r > 0 && r < 100) {
            assertEquals(r, player.getHealth());
        } else {
            assertEquals(100, player.getHealth());
            if (r >= 100) {
                player.increaseHealth(Integer.MAX_VALUE-100);
                assertEquals(r, player.getHealth());
            }
        }
        //reset
        player.setMaxHealth(100);
        player.increaseHealth(Integer.MAX_VALUE-100);
    }

    int testMaxHealth = 200;
    @Property
    void increaseHealthTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 100)int i) {
        player = createPlayer();
        player.setMaxHealth(testMaxHealth);
        player.increaseHealth(i);
        if (i < 0) {
            assertEquals(100, player.getHealth());
        } else {
            assertEquals(Math.min(100 + i, testMaxHealth), player.getHealth());
            //reset
            player.setMaxHealth(100);
        }
    }

    @Property
    void decreaseHealthTest(@ForAll int i) {
        player = createPlayer();
        player.decreaseHealth(i);
        if (i < 0) {
            assertEquals(100, player.getHealth());
        } else {
            assertEquals(Math.max(100 - i, 0), player.getHealth());
            //reset
            player.increaseHealth(100);
        }
    }
    @Property
    void setMaxAmmoTest(@ForAll int r) {
        player = createPlayer();
        assertEquals(30, player.getAmmo());
        player.setMaxAmmo(r);
        if (r > 0 && r < 30) {
            assertEquals(r, player.getAmmo());
        } else {
            assertEquals(30, player.getAmmo());
            if (r >= 30) {
                player.increaseAmmo(Integer.MAX_VALUE-30);
                assertEquals(r, player.getAmmo());
            }
        }
        //reset
        player.setMaxAmmo(30);
        player.increaseAmmo(Integer.MAX_VALUE-30);
    }

    int testMaxAmmo = 200;
    @Property
    void increaseAmmoTest(@ForAll @IntRange(max = Integer.MAX_VALUE - 30)int i) {
        player = createPlayer();
        player.setMaxAmmo(testMaxAmmo);
        player.increaseAmmo(i);
        if (i < 0) {
            assertEquals(30, player.getAmmo());
        } else {
            assertEquals(Math.min(30 + i, testMaxAmmo), player.getAmmo());
            //reset
            player.setMaxAmmo(30);
        }
    }

    @Property
    void decreaseAmmoTest(@ForAll int i) {
        player = createPlayer();
        player.decreaseAmmo(i);
        if (i < 0) {
            assertEquals(30, player.getAmmo());
        } else {
            assertEquals(Math.max(30 - i, 0), player.getAmmo());
            //reset
            player.increaseAmmo(30);
        }
    }*/
}