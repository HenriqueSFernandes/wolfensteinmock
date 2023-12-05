package org.wolfenstein.model.image;

import com.googlecode.lanterna.graphics.TextGraphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    private static ImageLoader instance;
    private List<Image> images = new ArrayList<>();

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    public boolean importImage(String imageName) throws IOException {
        // This assumes the image is inside /resources/images
        URL resource = getClass().getResource("/images/" + imageName);
        if (resource == null) {
            return false;
        }
        BufferedImage loadedImage = ImageIO.read(resource);
        Image image = new Image(loadedImage);
        images.add(image);
        return true;
    }

    public void drawAllImages(TextGraphics graphics) {
        for (Image image : images) {
            image.draw(graphics);
        }
    }
}
