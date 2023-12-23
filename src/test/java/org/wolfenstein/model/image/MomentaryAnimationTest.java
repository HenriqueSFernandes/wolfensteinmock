package org.wolfenstein.model.image;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class MomentaryAnimationTest {
    AnimationLoader testAnimationLoad;
    Animation testAnimation;

    @BeforeEach
    void initializeAnimation() throws IOException {
        testAnimationLoad = AnimationLoader.getInstance();
        testAnimationLoad.getLoadedAnimations().clear();
        testAnimationLoad.importMomentaryAnimation("pistol_firing.png", new Position(0, 0));
        testAnimation = testAnimationLoad.getAnimation(0);
    }

    @Test
    void nextFrameTest() {
        testAnimation.play();
        for (int i = 0; i < testAnimation.frameAmount; i++) {
            assertTrue(testAnimation.playing);
            assertEquals(testAnimation.frames.get(i), testAnimation.currentFrame);
            testAnimation.nextFrame();
        }
        assertEquals(testAnimation.frames.get(0), testAnimation.currentFrame);
        assertFalse(testAnimation.playing);
    }

    @Test
    void drawTest() {
        TextGraphics testGraphics = mock(TextGraphics.class);
        when(testGraphics.getCharacter(anyInt(), anyInt())).thenReturn(new TextCharacter(' '));

        testAnimation.play();
        for (int i = 0; i < testAnimation.frameAmount; i++) {
            assertTrue(testAnimation.playing);
            Image currentFrame = testAnimation.frames.get(i);
            assertEquals(currentFrame, testAnimation.currentFrame);
            testAnimation.draw(testGraphics);

            LinkedHashMap<TextColor, Integer> pixelNum = new LinkedHashMap<>();
            List<TextColor> colorsUsed = new ArrayList<>();

            for (int y = 0; y < currentFrame.getImage().getHeight(); y++) {
                for (int x = 0; x < currentFrame.getImage().getWidth(); x++) {
                    if (currentFrame.getAlpha(currentFrame.getImage().getRGB(x, y)) != 255) continue;
                    if (pixelNum.get(currentFrame.toRGB(currentFrame.getImage().getRGB(x, y))) == null) {
                        colorsUsed.add(currentFrame.toRGB(currentFrame.getImage().getRGB(x, y)));
                        pixelNum.put(currentFrame.toRGB(currentFrame.getImage().getRGB(x, y)), 1);
                    } else {
                        pixelNum.put(currentFrame.toRGB(currentFrame.getImage().getRGB(x, y)), pixelNum.get(currentFrame.toRGB(currentFrame.getImage().getRGB(x, y))) + 1);
                    }
                }
            }

            for (TextColor c : colorsUsed) {
                verify(testGraphics, atLeast(pixelNum.get(c))).setBackgroundColor(c);
            }
        }
    }
}
