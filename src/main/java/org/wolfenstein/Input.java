package org.wolfenstein;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Input extends Thread{
    public final Queue<KeyStroke> keyStrokeQueue = new LinkedList<KeyStroke>();
    private final TerminalScreen screen;

    public Input(TerminalScreen screen) {
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
