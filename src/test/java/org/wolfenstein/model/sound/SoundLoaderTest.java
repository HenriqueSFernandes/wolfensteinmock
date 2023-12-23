package org.wolfenstein.model.sound;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SoundLoaderTest {
    SoundLoader testSoundLoad;

    @Test
    void importSoundTest() {
        testSoundLoad = SoundLoader.getInstance();
        testSoundLoad.clearAllSounds();
        assertTrue(testSoundLoad.getLoadedSounds().isEmpty());
        boolean actionSuccess = testSoundLoad.importSound("a.wav");
        assertFalse(actionSuccess);
        assertTrue(testSoundLoad.getLoadedSounds().isEmpty());
        actionSuccess = testSoundLoad.importSound("gun_shot.wav");
        assertTrue(actionSuccess);
        assertFalse(testSoundLoad.getLoadedSounds().isEmpty());
    }

    @Test
    void getLoadedSounds() {
        testSoundLoad = SoundLoader.getInstance();
        testSoundLoad.clearAllSounds();
        assertTrue(testSoundLoad.getLoadedSounds().isEmpty());
        testSoundLoad.importSound("gun_shot.wav");
        assertEquals(1, testSoundLoad.getLoadedSounds().size());
    }
}
