package org.wolfenstein.model.sound;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SoundLoaderTest {
    SoundLoader testSoundLoad;
    @Test
    void importSoundTest() throws IOException {
        testSoundLoad = SoundLoader.getInstance();
        assertTrue(testSoundLoad.getLoadedSounds().isEmpty());
        testSoundLoad.importSound("gun_shot.wav");
        assertFalse(testSoundLoad.getLoadedSounds().isEmpty());
    }
}
