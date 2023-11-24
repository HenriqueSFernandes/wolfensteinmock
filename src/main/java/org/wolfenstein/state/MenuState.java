package org.wolfenstein.state;

import org.wolfenstein.viewer.MenuViewer;
import org.wolfenstein.viewer.Viewer;
import org.wolfenstein.controller.Controller;
import org.wolfenstein.controller.MenuController;
import org.wolfenstein.model.Menu;

public class MenuState extends State<Menu> {
    public MenuState(Menu model) {
        super(model);
    }
    @Override
    protected Controller<Menu> getController() {
        return new MenuController(getModel());
    }
    @Override
    protected Viewer<Menu> getViewer() {
        return new MenuViewer(getModel());
    }
}
