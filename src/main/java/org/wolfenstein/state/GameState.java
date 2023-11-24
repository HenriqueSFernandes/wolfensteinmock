package org.wolfenstein.state;

import org.wolfenstein.viewer.GameViewer;
import org.wolfenstein.viewer.Viewer;
import org.wolfenstein.controller.CameraController;
import org.wolfenstein.controller.Controller;
import org.wolfenstein.model.Camera;

public class GameState extends State<Camera> {
    public GameState(Camera model) {
        super(model);
    }
    @Override
    protected Controller<Camera> getController() {
        return new CameraController(getModel());
    }
    @Override
    protected Viewer<Camera> getViewer() {
        return new GameViewer(getModel());
    }
}
