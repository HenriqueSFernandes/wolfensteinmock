package org.wolfenstein.model.image;

import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageLoaderTest {
    ImageLoader testImageLoad;
    @Test
    void importImageTest() throws IOException {
        testImageLoad = ImageLoader.getInstance();
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
}
