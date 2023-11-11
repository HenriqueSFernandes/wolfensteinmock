package org.game;

import org.engine.Window;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
         try {
            org.engine.Window window = new Window();
            window.run();
        } catch (IOException | URISyntaxException | FontFormatException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}