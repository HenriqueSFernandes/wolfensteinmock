package org.wolfenstein.GUI;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.wolfenstein.model.Map;
import org.wolfenstein.model.Position;

import java.io.IOException;

public interface GUI {
    void stopScreen() throws IOException;

    void refresh() throws IOException;

    void clear();

    GUIAction getNextAction() throws IOException;

    void drawMap(Map map);

    void drawText(int x, int y, String text);

    void drawPlayerCamera(Position position, Map map) throws IOException;

    void drawGuard(int index, Position position, Map map) throws IOException;

    void drawFloor();

    void drawCeiling();

    void drawHearts();

    void drawAim();

    TextGraphics getGraphics();

    void drawGuardCounter() throws IOException;

    enum GUIAction {FRONT, RIGHT, BACK, LEFT, NONE, QUIT, SELECT, SKIP, FIRE}
}
