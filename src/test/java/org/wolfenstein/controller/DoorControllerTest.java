package org.wolfenstein.controller;

import org.junit.jupiter.api.Test;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Door;
import org.wolfenstein.model.elements.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DoorControllerTest {
    @Test
    public void constructorTest() {
        Camera mockCamera = mock(Camera.class);
        DoorController testController = new DoorController(mockCamera);

        assertEquals(mockCamera, testController.getModel());
    }

    @Test
    public void openTest() {
        Camera mockCamera = mock(Camera.class);
        Game mockGame = mock(Game.class);
        DoorController testController = new DoorController(mockCamera);
        Door mockDoor = mock(Door.class);
        List<Door> result = new ArrayList<>();
        result.add(mockDoor);
        Player mockPlayer = mock(Player.class);

        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockCamera.getDoors()).thenReturn(result);
        when(mockPlayer.getPosition()).thenReturn(new Position(10, 10));
        when(mockDoor.getPosition()).thenReturn(new Position(10, 11));

        testController.step(mockGame, GUI.GUIAction.SELECT, 0);

        verify(mockDoor).setOpen(true);
    }

    @Test
    public void closeTest() {
        Camera mockCamera = mock(Camera.class);
        Game mockGame = mock(Game.class);
        DoorController testController = new DoorController(mockCamera);
        Door mockDoor = mock(Door.class);
        List<Door> result = new ArrayList<>();
        result.add(mockDoor);
        Player mockPlayer = mock(Player.class);

        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockCamera.getDoors()).thenReturn(result);
        when(mockPlayer.getPosition()).thenReturn(new Position(10, 10));
        when(mockDoor.getPosition()).thenReturn(new Position(10, 30));
        when(mockDoor.isOpen()).thenReturn(true);

        testController.step(mockGame, GUI.GUIAction.NONE, 0);

        verify(mockDoor).setOpen(false);
    }
}
