package org.wolfenstein.model.sound;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SoundLoaderTest {
    SoundLoader testSoundLoad;
    @Test
    void importSoundTest() {
        testSoundLoad = SoundLoader.getInstance();
        int testSize = testSoundLoad.getLoadedSounds().size();
        testSoundLoad.importSound("gun_shot.wav");
        assertEquals(testSize + 1, testSoundLoad.getLoadedSounds().size());
    }
}
