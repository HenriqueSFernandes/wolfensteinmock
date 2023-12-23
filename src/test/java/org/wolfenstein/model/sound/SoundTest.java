package org.wolfenstein.model.sound;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class SoundTest {
    @Test
    void play() {
        try {
            Sound sound = new Sound(getClass().getResource("/sounds/gun_shot.wav"));
            sound.play();
        } catch (Exception e) {
            fail();
        }
        Sound sound = new Sound(getClass().getResource("/images/aim.png"));
        try {
            sound.play();
        } catch (RuntimeException e) {
        }
    }
}
