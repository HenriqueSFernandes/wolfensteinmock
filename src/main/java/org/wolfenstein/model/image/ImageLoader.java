package org.wolfenstein.model.image;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.wolfenstein.model.Position;

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

    public boolean importImage(String imageName, Position position) throws IOException {
        URL resource = getClass().getResource("/images/" + imageName);
        if (resource == null) {
            return false;
        }
        BufferedImage loadedImage = ImageIO.read(resource);
        Image image = new Image(loadedImage, position, imageName);
        images.add(image);
        return true;
    }

    public void drawAllImages(TextGraphics graphics) {
        for (Image image : images) {
            image.draw(graphics);
        }
    }
    public Image getImage(int index){
        return images.get(index);
    }
}
