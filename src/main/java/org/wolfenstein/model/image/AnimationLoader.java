package org.wolfenstein.model.image;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.wolfenstein.model.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AnimationLoader {
    private static AnimationLoader instance;
    private List<Animation> animations = new ArrayList<>();

    private AnimationLoader() {
    }

    public static AnimationLoader getInstance() {
        if (instance == null) {
            instance = new AnimationLoader();
        }
        return instance;
    }

    public boolean importSprite(String spriteName, Position position) throws IOException {
        URL resource = getClass().getResource("/animations/" + spriteName);
        if (resource == null) {
            return false;
        }
        BufferedImage loadedImage = ImageIO.read(resource);
        Image image = new Image(loadedImage);
        Animation animation = new Animation(image, position, 10);
        animations.add(animation);
        return true;
    }

    public void drawAllAnimations(TextGraphics graphics) {
        for (Animation animation : animations) {
            animation.draw(graphics);
        }

    }
}
