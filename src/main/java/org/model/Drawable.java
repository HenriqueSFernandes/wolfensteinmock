package org.model;

import com.googlecode.lanterna.graphics.TextGraphics;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public interface Drawable {
    public void draw(TextGraphics graphics) throws IOException, URISyntaxException, FontFormatException;
}
