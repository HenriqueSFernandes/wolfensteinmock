package org.wolfenstein.GUI;

import org.wolfenstein.model.Map;
import org.wolfenstein.model.Position;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public interface GUI {
    public void stopScreen() throws IOException;
    public void refresh() throws IOException;
    public void clear();
    GUIAction getNextAction() throws IOException;
    public void drawMap(Map map);
    public void drawText(int x, int y, String text);
    public void drawPlayerCamera(Position position, Map map);
    public void drawFloor();
    public void drawCeiling();
    public void drawGuard();

    enum GUIAction {FRONT, RIGHT, BACK, LEFT, NONE, QUIT, SELECT, FIRE}
}
