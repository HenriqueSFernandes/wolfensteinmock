package org.wolfenstein.model.sound;

import org.junit.jupiter.api.Test;

import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SoundLoaderTest {
    SoundLoader testSoundLoad;
    @Test
    void importSoundTest() throws IOException {
        testSoundLoad = SoundLoader.getInstance();
        testSoundLoad.clearAllSounds();
        assertTrue(testSoundLoad.getLoadedSounds().isEmpty());
        boolean actionSuccess = testSoundLoad.importSound("a.wav");
        assertFalse(actionSuccess);
        assertTrue(testSoundLoad.getLoadedSounds().isEmpty());
        testSoundLoad.importSound("gun_shot.wav");
        assertFalse(testSoundLoad.getLoadedSounds().isEmpty());
    }
}
