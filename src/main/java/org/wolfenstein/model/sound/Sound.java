package org.wolfenstein.model.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private String soundName;
    private URL resource;

    public Sound(String soundName) {
        this.soundName = soundName;
        resource = getClass().getResource("/sounds/" + soundName);
    }

    public void play() {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(resource);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
