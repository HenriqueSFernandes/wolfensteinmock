package org.wolfenstein.GUI;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.wolfenstein.model.Map;
import org.wolfenstein.model.Position;

import java.io.IOException;

public interface GUI {
    public void stopScreen() throws IOException;
    public void refresh() throws IOException;
    public void clear();
    GUIAction getNextAction() throws IOException;
    public void drawMap(Map map);
    public void drawText(int x, int y, String text);
    public void drawPlayerCamera(Position position, Map map) throws IOException;
    public void drawGuard(int index, Position position, Map map) throws IOException;
    public void drawFloor();
    public void drawCeiling();
    public void drawHearts();
    public void drawAim();
    public TextGraphics getGraphics();
    public void drawGuardCounter() throws IOException;
    enum GUIAction {FRONT, RIGHT, BACK, LEFT, NONE, QUIT, SELECT, SKIP, FIRE}
}
