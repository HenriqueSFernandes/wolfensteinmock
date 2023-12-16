package org.wolfenstein.controller;

import org.junit.jupiter.api.Test;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Map;
import org.wolfenstein.model.MapLoader;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Player;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class PlayerControllerTest {
    Camera mockCamera = mock(Camera.class);
    Player mockPlayer = mock(Player.class);
    Position mockPosition = mock(Position.class);
    Game mockGame = mock(Game.class);
    Map mockMap = mock(Map.class);
    MapLoader mockMapLoader = mock(MapLoader.class);
    PlayerController testController = new PlayerController(mockCamera);

    @Test
    public void moveTest() throws IOException {
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPosition()).thenReturn(mockPosition);
        when(mockCamera.getMap()).thenReturn(mockMap);
        when(mockMap.nextRoomPosition()).thenReturn(mockPosition);
        when(mockMap.getMapLoader()).thenReturn(mockMapLoader);

        testController.step(mockGame, GUI.GUIAction.FRONT, 0);
        testController.step(mockGame, GUI.GUIAction.BACK, 0);
        testController.step(mockGame, GUI.GUIAction.LEFT, 0);
        testController.step(mockGame, GUI.GUIAction.RIGHT, 0);

        verify(mockPosition).moveForward();
        verify(mockPosition).rotateAntiClockwise();
        verify(mockPosition).moveBackwards();
        verify(mockPosition).rotateClockwise();
    }

    @Test
    public void nextMapTest() throws IOException {
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPosition()).thenReturn(mockPosition);
        when(mockCamera.getMap()).thenReturn(mockMap);
        when(mockMap.nextRoomPosition()).thenReturn(mockPosition);
        when(mockMap.getMapLoader()).thenReturn(mockMapLoader);
        when(mockMap.getCellsize()).thenReturn(8);
        when(mockPosition.getX()).thenReturn(12.0);
        when(mockPosition.getY()).thenReturn(12.0);
        when(mockMap.playerStartPosition()).thenReturn(mockPosition);

        testController.step(mockGame, GUI.GUIAction.NONE, 0);

        verify(mockMap).setMap(any());
        verify(mockPlayer).setPosition(mockPosition);
        verify(mockCamera).createGuardList();
        verify(mockCamera).createDoors();
    }
}
