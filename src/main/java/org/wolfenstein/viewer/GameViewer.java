package org.wolfenstein.viewer;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.model.Camera;

public class GameViewer extends Viewer<Camera> {

    public GameViewer(Camera model) {
        super(model);
    }
    private final MapViewer mapViewer = new MapViewer(getModel().getMap());
    @Override
    protected void drawElements(GUI gui) {
        mapViewer.drawElements(gui);
        gui.drawPlayerCamera(getModel().getPlayer().getPosition(), getModel().getMap());
    }
}
