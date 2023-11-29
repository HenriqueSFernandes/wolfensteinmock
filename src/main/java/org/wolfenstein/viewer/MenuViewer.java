package org.wolfenstein.viewer;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.model.Menu;

public class MenuViewer extends Viewer<Menu> {
    public MenuViewer(Menu model) {
        super(model);
    }

    @Override
    protected void drawElements(GUI gui) {
        gui.drawText(20, 20, "Menu");
        gui.drawText(20, 23, "Begin");
        gui.drawText(20, 24, "Exit");
    }
}
