package org.wolfenstein.model.image;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.wolfenstein.model.Position;

import java.awt.image.BufferedImage;

public class Image {
    private BufferedImage image;
    private Position position;

    public Image(BufferedImage image) {
        this.image = image;
        this.position = new Position(0, 0);
    }

    public Image(BufferedImage image, Position position) {
        this.image = image;
        this.position = position;
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

    private int getAlpha(int color) {
        return (color >> 24) & 0xFF;
    }

    private TextColor.RGB toRGB(int color) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        return new TextColor.RGB(red, green, blue);

    }

    public void draw(TextGraphics graphics) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixelPositionX = x + (int) position.getX();
                int pixelPositionY = y + (int) position.getY();
                int imageColor = image.getRGB(x, y);
                int alpha = getAlpha(imageColor);
                TextColor.RGB currentColor = (TextColor.RGB) graphics.getCharacter(pixelPositionX, pixelPositionY).getBackgroundColor();
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
