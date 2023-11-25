package org.wolfenstein.viewer;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.model.Map;

public class MapViewer extends Viewer<Map> {
    public MapViewer(Map model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) {
        gui.drawMap(getModel());
    }
}
