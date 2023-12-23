package org.wolfenstein.model.image;

import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Position;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ImageLoaderTest {
    ImageLoader testImageLoad;
    @Test
    void importImageTest() throws IOException {
        testImageLoad = ImageLoader.getInstance();
        int testImages = testImageLoad.getLoadedImages().size();
        boolean actionSuccess = testImageLoad.importImage("a.png", new Position(0, 0));
        assertFalse(actionSuccess);
        assertEquals(testImages, testImageLoad.getLoadedImages().size());
        actionSuccess = testImageLoad.importImage("image_test.png", new Position(0, 0));
        assertTrue(actionSuccess);
        assertFalse(testImageLoad.getLoadedImages().isEmpty());
    }
}
