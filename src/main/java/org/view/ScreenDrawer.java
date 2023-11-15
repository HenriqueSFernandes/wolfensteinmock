package org.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.model.Drawable;
import org.model.Map;

import java.io.IOException;
import java.util.ArrayList;

public class ScreenDrawer {
    private final LanternaScreen screen;
    private static Map gameMap;
    private static final ArrayList<Drawable> drawableEntities = new ArrayList<Drawable>();
    private static TextGraphics graphics;
    public static final String BLACK = "#000000";
    public static final String WHITE = "#FFFFFF";
    public static final String GRAY = "#808080";
    final int WIDTH;
    final int HEIGHT;

    public ScreenDrawer(LanternaScreen screen, Map gameMap) {
        this.screen = screen;
        ScreenDrawer.gameMap = gameMap;
        graphics = screen.newTextGraphics();
        WIDTH = gameMap.getWidth() * 16;
        HEIGHT = gameMap.getHeight() * 16;
    }
    public void addToDrawableEntities(Drawable entity) {drawableEntities.add(entity);}
    public void removeFromDrawableEntities(Drawable entity) {drawableEntities.remove(entity);}
    public void draw() throws IOException {
        screen.clear();
        graphics.setBackgroundColor(TextColor.Factory.fromString(GRAY));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(WIDTH, HEIGHT), ' ');
        // Adjust the size of each cell (square) and border
        int cellSize = 16;
        int borderSize = 1;

        int[][] map = gameMap.getMap();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                int cellValue = map[y][x];
                TextColor cellColor;

                if (cellValue == 0) {
                    cellColor = TextColor.Factory.fromString(WHITE);
                } else {
                    cellColor = TextColor.Factory.fromString(BLACK);
                }

                for (int i = 0; i < cellSize; i++) {
                    for (int j = 0; j < cellSize; j++) {
                        // Draw the border
                        if (i < borderSize || i >= cellSize - borderSize || j < borderSize || j >= cellSize - borderSize) {
                            graphics.setBackgroundColor(TextColor.Factory.fromString(GRAY));
                        } else {
                            graphics.setBackgroundColor(cellColor);
                        }
                        // Draw the square
                        graphics.putString(x * cellSize + i, y * cellSize + j, " ");
                    }
                }
            }
        }
        for (Drawable entity : drawableEntities) entity.draw(graphics);

        screen.refresh();
    }
}
