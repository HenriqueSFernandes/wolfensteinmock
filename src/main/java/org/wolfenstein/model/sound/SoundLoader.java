package org.wolfenstein.model.sound;

import java.util.ArrayList;
import java.util.List;

public class SoundLoader {
    private static SoundLoader instance;
    List<Sound> sounds = new ArrayList<>();

    private SoundLoader() {
    }

    public static SoundLoader getInstance() {
        if (instance == null) {
            instance = new SoundLoader();
        }
        return instance;
    }

    public void importSound(String soundName) {

        sounds.add(new Sound(soundName));
    }

    public void playAllSounds() {
        for (Sound sound : sounds) {
            sound.play();
        }
    }

    public Sound getSound(int index) {
        return sounds.get(index);
    }
    public List<Sound> getLoadedSounds(){
        return sounds;
    }
}
