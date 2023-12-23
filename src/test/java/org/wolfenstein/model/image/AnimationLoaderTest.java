package org.wolfenstein.model.image;

import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimationLoaderTest {
    AnimationLoader testAnimationLoad;
    @Test
    void importMomentaryAnimationTest() throws IOException {
        testAnimationLoad = AnimationLoader.getInstance();
        testAnimationLoad.clearAllAnimations();
        assertTrue(testAnimationLoad.getLoadedAnimations().isEmpty());
        // try to import animation that doesn't exist:
        boolean actionSuccess = testAnimationLoad.importMomentaryAnimation("a.png", new Position(0, 0));
        assertFalse(actionSuccess);
        assertTrue(testAnimationLoad.getLoadedAnimations().isEmpty());
        // try to import animation that does exist:
        actionSuccess = testAnimationLoad.importMomentaryAnimation("enemy_dying.png", new Position(0, 0));
        assertTrue(actionSuccess);
        assertFalse(testAnimationLoad.getLoadedAnimations().isEmpty());
    }
    @Test //Same as the previous one
    void importContinuousAnimationTest() throws IOException {
        testAnimationLoad = AnimationLoader.getInstance();
        testAnimationLoad.clearAllAnimations();
        assertTrue(testAnimationLoad.getLoadedAnimations().isEmpty());
        // try to import animation that doesn't exist:
        boolean actionSuccess = testAnimationLoad.importContinuousAnimation("a.png", new Position(0, 0));
        assertFalse(actionSuccess);
        assertTrue(testAnimationLoad.getLoadedAnimations().isEmpty());
        // try to import animation that does exist:
        actionSuccess = testAnimationLoad.importContinuousAnimation("enemy_dying.png", new Position(0, 0));
        assertTrue(actionSuccess);
        assertFalse(testAnimationLoad.getLoadedAnimations().isEmpty());
    }
}
