package org.wolfenstein.model.image;

import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AnimationTest {
    AnimationLoader animationLoader = AnimationLoader.getInstance();

    @Test
    void setPlaying() throws IOException {
        animationLoader.clearAllAnimations();
        animationLoader.importMomentaryAnimation("enemy_dying.png", new Position(0, 0));
        animationLoader.getAnimation(0).setPlaying(true);
        assertTrue(animationLoader.getAnimation(0).playing);
        animationLoader.getAnimation(0).setPlaying(false);
        assertFalse(animationLoader.getAnimation(0).playing);
    }

    @Test
    void getCurrentFrame() throws IOException {
        animationLoader.clearAllAnimations();
        animationLoader.importMomentaryAnimation("enemy_dying.png", new Position(0, 0));
        Animation animation = animationLoader.getAnimation(0);
        assertEquals(animation.getCurrentFrame(), animation.frames.get(0));
    }

    @Test
    void getAnimation() throws IOException {
        animationLoader.clearAllAnimations();
        animationLoader.importMomentaryAnimation("enemy_dying.png", new Position(0, 0));
        Animation animation = animationLoader.getAnimation(0);
        assertEquals(animation.getAnimation(), animation.frames);
    }
}