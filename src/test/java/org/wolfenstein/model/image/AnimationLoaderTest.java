package org.wolfenstein.model.image;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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

    @Test
    void drawAllAnimations() {
        Animation mockAnimation1 = mock(Animation.class);
        Animation mockAnimation2 = mock(Animation.class);
        TextGraphics mockTextGraphics = mock(TextGraphics.class);
        AnimationLoader animationLoader = AnimationLoader.getInstance();
        animationLoader.clearAllAnimations();
        animationLoader.animations.add(mockAnimation1);
        animationLoader.animations.add(mockAnimation2);
        animationLoader.drawAllAnimations(mockTextGraphics);
        verify(mockAnimation1, times(1)).draw(mockTextGraphics);
        verify(mockAnimation2, times(1)).draw(mockTextGraphics);

    }
}
