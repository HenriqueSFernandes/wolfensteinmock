package org.wolfenstein.controller;

import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Map;

public abstract class GameController extends Controller<Camera> {
    public GameController(Camera model) {
        super(model);
    }
}
