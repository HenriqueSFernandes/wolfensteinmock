package org.wolfenstein.model.image;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ImageLoaderTest {
    ImageLoader testImageLoad;
    @Test
    void importImageTest() throws IOException {
        testImageLoad = ImageLoader.getInstance();
        testImageLoad.clearAllImages();
        assertTrue(testImageLoad.getLoadedImages().isEmpty());
        // try to import image that doesn't exist:
        boolean actionSuccess = testImageLoad.importImage("a.png", new Position(0, 0));
        assertFalse(actionSuccess);
        assertTrue(testImageLoad.getLoadedImages().isEmpty());
        // try to import image that does exist:
        actionSuccess = testImageLoad.importImage("image_test.png", new Position(0, 0));
        assertTrue(actionSuccess);
        assertFalse(testImageLoad.getLoadedImages().isEmpty());
    }

    @Test
    void drawAllImages() {
        Image mockImage1 = mock(Image.class);
        Image mockImage2 = mock(Image.class);
        TextGraphics mockTextGraphics = mock(TextGraphics.class);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.clearAllImages();
        imageLoader.images.add(mockImage1);
        imageLoader.images.add(mockImage2);
        imageLoader.drawAllImages(mockTextGraphics);
        verify(mockImage1, times(1)).draw(mockTextGraphics);
        verify(mockImage2, times(1)).draw(mockTextGraphics);

    }
}
