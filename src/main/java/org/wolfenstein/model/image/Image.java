package org.wolfenstein.model.image;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.wolfenstein.model.Position;

import java.awt.image.BufferedImage;

public class Image {
    private BufferedImage image;
    private Position position;
    private final String imageName;

    private boolean active;

    public Image(BufferedImage image, String imageName) {
        this.image = image;
        this.position = new Position(0, 0);
        this.active = true;
        this.imageName = imageName;
    }

    public Image(BufferedImage image, Position position, String imageName) {
        this.image = image;
        this.position = position;
        this.active = true;
        this.imageName = imageName;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getImageName() {
        return imageName;
    }

    public int getAlpha(int color) {
        return (color >> 24) & 0xFF;
    }

    public TextColor.RGB toRGB(int color) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        return new TextColor.RGB(red, green, blue);

    }

    public void draw(TextGraphics graphics) {
        if (active) {
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixelPositionX = x + (int) position.getX();
                    int pixelPositionY = y + (int) position.getY();
                    int imageColor = image.getRGB(x, y);
                    int alpha = getAlpha(imageColor);
                    TextColor currentColor = graphics.getCharacter(pixelPositionX, pixelPositionY).getBackgroundColor();
                    if (alpha == 255) {
                        graphics.setBackgroundColor(toRGB(imageColor));
                    } else {
                        graphics.setBackgroundColor(currentColor);
                    }

                    graphics.setCharacter(x + (int) position.getX(), y + (int) position.getY(), ' ');
                }
            }
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
