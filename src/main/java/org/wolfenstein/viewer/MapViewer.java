package org.wolfenstein.viewer;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.model.Map;

public class PlayerViewer extends Viewer<Map> {
    public PlayerViewer(Map model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) {
        gui.drawMap(getModel());
    }
}
