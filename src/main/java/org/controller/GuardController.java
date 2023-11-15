package org.controller;

import org.model.Guard;
import org.model.Position;

public class GuardController extends Guard {
    public GuardController(Position position) {
        super(position);
    }

    public void move() {super.rotateAntiClockwise();}
}
