package org.wolfenstein.viewer;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.elements.Guard;

import java.io.IOException;

public class GameViewer extends Viewer<Camera> {

    public GameViewer(Camera model) {
        super(model);
    }
    private final MapViewer mapViewer = new MapViewer(getModel().getMap());
    @Override
    protected void drawElements(GUI gui) throws IOException {
        mapViewer.drawElements(gui);
        gui.drawFloor();
        gui.drawCeiling();
        gui.drawPlayerCamera(getModel().getPlayer().getPosition(), getModel().getMap());
        for (int i = 0; i < getModel().getGuardList().size(); i++) {
            if (getModel().getGuardList().get(i).getHealth() > 0)
                gui.drawGuard(i, getModel().getGuardList().get(i).getPosition(), getModel().getMap());
        }
        gui.drawHearts();
        gui.drawAim();
    }
}
