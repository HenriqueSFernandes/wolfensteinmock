package org.wolfenstein.viewer;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.model.Camera;

public class GameViewer extends Viewer<Camera> {

    public GameViewer(Camera model) {
        super(model);
    }
    private final PlayerViewer playerViewer = new PlayerViewer(getModel().getMap());
    @Override
    protected void drawElements(GUI gui) {
        playerViewer.drawElements(gui);
        gui.drawPlayerCamera(getModel().getPlayer().getPosition(), getModel().getMap());
    }
}
