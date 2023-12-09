package org.wolfenstein.model;

import org.wolfenstein.model.image.ImageLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Menu {
    private final List<String> entries;
    ImageLoader imageLoader = ImageLoader.getInstance();
    private int currentEntry = 0;

    public Menu() {
        this.entries = Arrays.asList("Start", "Exit");
        imageLoader.getImage(12).setActive(false);
    }

    public Menu(List<String> ent) {
        entries = ent;
    }

    public void nextEntry() {
        currentEntry++;
        if (currentEntry > this.entries.size() - 1) currentEntry = 0;
        if (currentEntry == 0) {
            imageLoader.getImage(11).setActive(true);
            imageLoader.getImage(12).setActive(false);
        } else {
            imageLoader.getImage(11).setActive(false);
            imageLoader.getImage(12).setActive(true);
        }
    }

    public void previousEntry() {
        currentEntry--;
        if (currentEntry < 0) currentEntry = this.entries.size() - 1;
        if (currentEntry == 0) {
            imageLoader.getImage(11).setActive(true);
            imageLoader.getImage(12).setActive(false);
        } else {
            imageLoader.getImage(11).setActive(false);
            imageLoader.getImage(12).setActive(true);
        }
    }

    public String getEntry(int i) {
        return entries.get(i);
    }

    public boolean isSelected(int i) {
        return currentEntry == i;
    }

    public boolean isSelectedExit() {
        return isSelected(1);
    }

    public boolean isSelectedStart() {
        return isSelected(0);
    }

    public int getNumberEntries() {
        return this.entries.size();
    }
}
