package org.wolfenstein.state;

import org.wolfenstein.*;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.viewer.Viewer;
import org.wolfenstein.controller.Controller;

import java.io.IOException;

public abstract class State<T> {
    private final T model;
    private final Controller<T> controller;
    private final Viewer<T> viewer;
    public State(T model) {
        this.model = model;
        this.controller = getController();
        this.viewer = getViewer();
    }
    protected abstract Controller<T> getController();
    protected abstract Viewer<T> getViewer();
    public T getModel() {
        return model;
    }
    public void step(GUI gui, Game game, long time) throws IOException {
        GUI.GUIAction action = gui.getNextAction();
        controller.step(game, action, time);
        viewer.draw(gui);
    }
}
