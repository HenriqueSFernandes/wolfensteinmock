package org.wolfenstein.controller;

import org.wolfenstein.model.Camera;

public abstract class GameController extends Controller<Camera> {
    public GameController(Camera model) {
        super(model);
    }
}
