package org.controller;

import com.googlecode.lanterna.input.KeyStroke;
import org.view.LanternaScreen;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class LanternaInput extends Thread{
    public final Queue<KeyStroke> keyStrokeQueue = new LinkedList<KeyStroke>();
    private final LanternaScreen screen;

    public LanternaInput(LanternaScreen screen) {
        this.screen = screen;
    }

    @Override
    public void run(){
        while(!isInterrupted()){
            try {
                keyStrokeQueue.add(screen.readInput());
            } catch (RuntimeException | IOException e) {
                this.interrupt();
            }
        }
    }
}
