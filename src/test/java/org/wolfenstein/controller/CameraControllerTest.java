package org.wolfenstein.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Map;
import org.wolfenstein.model.MapLoader;
import org.wolfenstein.model.elements.Player;
import org.wolfenstein.state.MenuState;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CameraControllerTest {

    private static Camera mockCamera;
    private static PlayerController mockPlayerController;
    private static DoorController mockDoorController;
    private static GuardController mockGuardController;
    private static Player mockPlayer;
    private static CameraController testController;
    private static Game mockGame;
    private static Map mockMap;
    private static MapLoader mockMapLoader;

    @BeforeEach
    public void createTestController() throws IOException {
        mockCamera = mock(Camera.class);
        mockPlayerController = mock(PlayerController.class);
        mockDoorController = mock(DoorController.class);
        mockGuardController = mock(GuardController.class);
        mockPlayer = mock(Player.class);
        mockGame = mock(Game.class);
        mockMap = mock(Map.class);
        mockMapLoader = mock(MapLoader.class);
        testController = new CameraController(mockCamera, mockPlayerController, mockGuardController, mockDoorController);

        when(mockPlayerController.getModel()).thenReturn(mockCamera);
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockCamera.getMap()).thenReturn(mockMap);
        when(mockMap.getMapLoader()).thenReturn(mockMapLoader);
        when(mockMapLoader.getNextMap()).thenReturn(null);
    }
    @Test
    public void quitTest() throws IOException {
        testController.step(mockGame, GUI.GUIAction.QUIT, 0);
        verify(mockGame).setState(any(MenuState.class));
    }

    @Test
    public void playerDeathTest() throws IOException {
        when(mockPlayer.getHealth()).thenReturn(0);
        testController.step(mockGame, GUI.GUIAction.NONE, 0);

        verify(mockGame).setState(any(MenuState.class));
        verify(mockPlayer).changeHealth(anyInt());
    }

    @Test
    public void skipTest() throws IOException {
        when(mockPlayer.getHealth()).thenReturn(20);
        testController.step(mockGame, GUI.GUIAction.SKIP, 0);

        verify(mockMapLoader).getNextMap();
        verify(mockMap).setMap(any());
        verify(mockPlayer).setPosition(any());
        verify(mockMap).playerStartPosition();
        verify(mockCamera).createDoors();
        verify(mockCamera).createGuardList();
    }

    @Test
    public void stepTest() throws IOException {
        when(mockPlayer.getHealth()).thenReturn(20);
        testController.step(mockGame, GUI.GUIAction.NONE, 0);

        verify(mockPlayerController).step(mockGame, GUI.GUIAction.NONE, 0);
        verify(mockDoorController).step(mockGame, GUI.GUIAction.NONE, 0);
        verify(mockGuardController).step(mockGame, GUI.GUIAction.NONE, 0);
    }

    @Test
    public void constructorTest() {
        CameraController cameraController = new CameraController(mockCamera);

        assertEquals(mockCamera, cameraController.getModel());
    }
}
