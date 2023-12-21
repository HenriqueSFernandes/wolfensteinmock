package org.wolfenstein.controller;

import org.junit.jupiter.api.Test;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Map;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Guard;
import org.wolfenstein.model.elements.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GuardControllerTest {
    Game mockGame = mock(Game.class);
    Camera mockCamera = mock(Camera.class);
    Guard mockGuard = mock(Guard.class);
    Vector<Guard> mockGuardList = new Vector<>();
    Map mockMap = mock(Map.class);
    Player mockPlayer = mock(Player.class);
    Position mockPosition = mock(Position.class);
    List<Position> mockList = new ArrayList<>();
    GuardController testController = new GuardController(mockCamera);

    @Test
    public void seeingPlayerTest() throws IOException {
        mockGuardList.clear();
        mockList.clear();
        mockGuardList.add(mockGuard);

        when(mockCamera.getGuardList()).thenReturn(mockGuardList);
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockGuard.getHealth()).thenReturn(20);
        when(mockGuard.getPosition()).thenReturn(mockPosition);
        when(mockPosition.lookForward()).thenReturn(mockList);
        when(mockCamera.getMap()).thenReturn(mockMap);
        when(mockMap.getCellsize()).thenReturn(8);
        when(mockMap.getWidth()).thenReturn(30);
        when(mockPlayer.getPosition()).thenReturn(mockPosition);
        when(mockPosition.viewAngle(mockPosition)).thenReturn(0.0);
        when(mockPosition.checkWall(mockPosition, mockMap)).thenReturn(false);

        mockList.add(new Position(-1, -1));

        testController.step(mockGame, GUI.GUIAction.NONE, 0);

        verify(mockGuard).setAggro(true);
    }
}
