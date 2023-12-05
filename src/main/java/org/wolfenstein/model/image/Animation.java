package org.wolfenstein.model.image;

import com.googlecode.lanterna.graphics.TextGraphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    List<Image> frames = new ArrayList<>();
    Image sprite; // Image with all frames
    int frameCounter = 0;
    Image currentFrame;

    private final int frameAmount;

    public Animation(Image sprite, int frameAmount) {
        this.frameAmount = frameAmount;
        this.sprite = sprite;
        int frameWidth = sprite.getImage().getWidth() / frameAmount;
        int frameHeight = sprite.getImage().getHeight();
        int frameType = sprite.getImage().getType();
        for (int i = 0; i < frameAmount; i++) {
            BufferedImage frame = new BufferedImage(frameWidth, frameHeight, frameType);
            for (int y = 0; y < frameHeight; y++){
                for (int x = 0; x < frameWidth; x++){
                    frame.setRGB(x, y, sprite.getImage().getRGB(i * frameWidth + x, y));
                }
            }
            Image frameImage = new Image(frame);
            frames.add(frameImage);
        }
        currentFrame = frames.get(0);
    }
    private void nextFrame(){
        frameCounter++;
        if (frameCounter >= frameAmount){
            frameCounter = 0;
        }
        currentFrame = frames.get(frameCounter);

    }

    public void draw(TextGraphics graphics){
        currentFrame.draw(graphics);
        nextFrame();
    }

}
