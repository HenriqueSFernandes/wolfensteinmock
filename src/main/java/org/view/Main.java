package org.view;

import org.view.Window;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) {
         try {
            Window window = new Window();
            window.run();
        } catch (IOException | URISyntaxException | FontFormatException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}