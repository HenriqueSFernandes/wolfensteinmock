package org.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import org.model.Drawable;
import org.model.Map;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.view.Window.*;

public class ScreenDrawer {
    private final LanternaScreen screen;
    private static Map gameMap;
    private static final ArrayList<Drawable> drawableEntities = new ArrayList<Drawable>();
    private static TextGraphics graphics;
    public static final TextColor.RGB BLACK = new TextColor.RGB(0, 0, 0);
    public static final TextColor.RGB WHITE = new TextColor.RGB(255, 255, 255);
    public static final TextColor.RGB GRAY = new TextColor.RGB(128, 128, 128);

    public ScreenDrawer(LanternaScreen screen, Map gameMap) {
        this.screen = screen;
        ScreenDrawer.gameMap = gameMap;
        graphics = screen.newTextGraphics();
    }
    public void addToDrawableEntities(Drawable entity) {drawableEntities.add(entity);}
    public void removeFromDrawableEntities(Drawable entity) {drawableEntities.remove(entity);}
    public void draw() throws IOException, URISyntaxException, FontFormatException {
        screen.clear();
        graphics.setBackgroundColor(GRAY);
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(WIDTH, HEIGHT), ' ');
        // Adjust the size of each cell (square) and border
        int borderSize = 1;

        int[][] map = gameMap.getMap();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                int cellValue = map[y][x];
                TextColor cellColor;

                if (cellValue == 0) {
                    cellColor = WHITE;
                } else {
                    cellColor = BLACK;
                }
//                  RENDER GRID
                for (int i = 0; i < CELLSIZE; i++) {
                    for (int j = 0; j < CELLSIZE; j++) {
                        // Draw the border
                        if (i < borderSize || i >= CELLSIZE - borderSize || j < borderSize || j >= CELLSIZE - borderSize) {
                            graphics.setBackgroundColor(GRAY);
                        } else {
                            graphics.setBackgroundColor(cellColor);
                        }

                        // Draw the square
                        graphics.putString(x * CELLSIZE + i, y * CELLSIZE + j, " ");
                    }
                }
            }
        }
        for (Drawable entity : drawableEntities) entity.draw(graphics);

        screen.refresh();
    }
}
