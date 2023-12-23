package org.wolfenstein.model.sound;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SoundLoader {
    private static SoundLoader instance;
    protected List<Sound> sounds = new ArrayList<>();

    private SoundLoader() {
    }

    public static SoundLoader getInstance() {
        if (instance == null) {
            instance = new SoundLoader();
        }
        return instance;
    }

    public boolean importSound(String soundName) {
        URL resource = getClass().getResource("/sounds/" + soundName);
        if (resource == null) {
            return false;
        }
        sounds.add(new Sound(resource));
        return true;
    }

    public Sound getSound(int index) {
        return sounds.get(index);
    }

    public List<Sound> getLoadedSounds() {
        return sounds;
    }

    public void clearAllSounds() {
        sounds = new ArrayList<>();
    }
}
