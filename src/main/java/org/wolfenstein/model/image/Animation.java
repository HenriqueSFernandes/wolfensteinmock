package org.wolfenstein.model.image;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.wolfenstein.model.Position;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Animation {
    protected final int frameAmount;
    protected List<Image> frames = new ArrayList<>();
    protected Image sprite; // Image with all frames
    protected int frameCounter = 0;
    protected Image currentFrame;
    protected Position position;
    protected boolean playing = false;

    public Animation(Image sprite, Position position, int frameAmount) {
        this.frameAmount = frameAmount;
        this.sprite = sprite;
        int frameWidth = sprite.getImage().getWidth() / frameAmount;
        int frameHeight = sprite.getImage().getHeight();
        int frameType = sprite.getImage().getType();
        for (int i = 0; i < frameAmount; i++) {
            BufferedImage frame = new BufferedImage(frameWidth, frameHeight, frameType);
            for (int y = 0; y < frameHeight; y++) {
                for (int x = 0; x < frameWidth; x++) {
                    frame.setRGB(x, y, sprite.getImage().getRGB(i * frameWidth + x, y));
                }
            }
            Image frameImage = new Image(frame, position);
            frames.add(frameImage);
        }
        currentFrame = frames.get(0);
    }

    protected abstract void nextFrame();

    public abstract void draw(TextGraphics graphics);

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
