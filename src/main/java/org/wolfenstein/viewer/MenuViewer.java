package org.wolfenstein.viewer;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.model.Menu;
import org.wolfenstein.model.image.ImageLoader;

public class MenuViewer extends Viewer<Menu> {
    private final ImageLoader imageLoader;

    public MenuViewer(Menu model) {
        super(model);
        imageLoader = ImageLoader.getInstance();
    }

    public MenuViewer(Menu model, ImageLoader imageLoader) {
        super(model);
        this.imageLoader = imageLoader;
    }

    @Override
    protected void drawElements(GUI gui) {
        if (getModel().isSelectedStart()) imageLoader.getImage(11).draw(gui.getGraphics());
        else if (getModel().isSelectedExit()) imageLoader.getImage(12).draw(gui.getGraphics());
    }
}
