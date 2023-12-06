package org.wolfenstein.viewer;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.elements.Guard;

public class GameViewer extends Viewer<Camera> {

    public GameViewer(Camera model) {
        super(model);
    }
    private final MapViewer mapViewer = new MapViewer(getModel().getMap());
    @Override
    protected void drawElements(GUI gui) {
        mapViewer.drawElements(gui);
        gui.drawFloor();
        gui.drawCeiling();
        gui.drawPlayerCamera(getModel().getPlayer().getPosition(), getModel().getMap());
        for (Guard guard : getModel().getGuardList()) {
            gui.drawGuard(guard.getPosition(), getModel().getMap());
        }
    }
}
