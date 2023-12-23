package org.wolfenstein.model.image;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ImageTest {
    ImageLoader testImageLoad;
    Image testImage;
    @Test
    void drawTest() throws IOException {
        testImageLoad = ImageLoader.getInstance();
        testImageLoad.importImage("image_test.png", new Position(0, 0));
        testImage = testImageLoad.getImage(0);

        TextGraphics testGraphics = mock(TextGraphics.class);
        when(testGraphics.getCharacter(anyInt(), anyInt())).thenReturn(new TextCharacter(' '));

        Dictionary<TextColor, Integer> pixelNum = new Hashtable<>();
        List<TextColor> colorsUsed = new ArrayList<>();
        for (int y = 0; y < testImage.getImage().getHeight(); y++) {
            for (int x = 0; x < testImage.getImage().getWidth(); x++) {
                if (testImage.getAlpha(testImage.getImage().getRGB(x, y)) != 255) continue;
                if (pixelNum.get(testImage.toRGB(testImage.getImage().getRGB(x, y))) == null) {
                    colorsUsed.add(testImage.toRGB(testImage.getImage().getRGB(x, y)));
                    pixelNum.put(testImage.toRGB(testImage.getImage().getRGB(x, y)), 1);
                } else {
                    pixelNum.put(testImage.toRGB(testImage.getImage().getRGB(x, y)), pixelNum.get(testImage.toRGB(testImage.getImage().getRGB(x, y))) + 1);
                }
            }
        }

        testImage.setActive(false);
        testImage.draw(testGraphics);
        for (int y = 0; y < testImage.getImage().getHeight(); y++) {
            for (int x = 0; x < testImage.getImage().getWidth(); x++) {
                verify(testGraphics, times(0)).setBackgroundColor(any());
            }
        }

        testImage.setActive(true);
        testImage.draw(testGraphics);
        for(TextColor c : colorsUsed) {
            verify(testGraphics, times(pixelNum.get(c))).setBackgroundColor(c);
        }
    }

    @Test
    void setImage() throws IOException {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.clearAllImages();
        imageLoader.importImage("aim.png", new Position(0, 0));
        imageLoader.importImage("heart.png", new Position(0, 0));
        imageLoader.getImage(0).setImage(imageLoader.getImage(1).getImage());
        assertEquals(imageLoader.getImage(0).getImage(), imageLoader.getImage(1).getImage());
    }

    @Test
    void getPosition() throws IOException {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.clearAllImages();
        imageLoader.importImage("aim.png", new Position(0, 0));
        assertEquals(imageLoader.getImage(0).getPosition(), new Position(0 ,0));
    }

    @Test
    void setPosition() throws IOException {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.clearAllImages();
        imageLoader.importImage("aim.png", new Position(0, 0));
        imageLoader.getImage(0).setPosition(new Position(1, 1));
        assertEquals(imageLoader.getImage(0).getPosition(), new Position(1, 1));
    }
}
