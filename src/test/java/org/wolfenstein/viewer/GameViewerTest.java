package org.wolfenstein.viewer;

import org.junit.jupiter.api.Test;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Guard;
import org.wolfenstein.model.elements.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GameViewerTest {
    Camera mockCamera = mock(Camera.class);
    GUI mockGUI = mock(GUI.class);
    Player mockPlayer = mock(Player.class);
    Position mockPosition = mock(Position.class);
    GameViewer testViewer = new GameViewer(mockCamera);
    List<Guard> mockGuardList = new ArrayList<>();
    Guard mockGuard = mock(Guard.class);

    @Test
    public void constructorTest() {
        assertEquals(mockCamera, testViewer.getModel());
    }

    @Test
    public void drawTest() throws IOException {
        mockGuardList.clear();
        mockGuardList.add(mockGuard);
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockCamera.getGuardList()).thenReturn(mockGuardList);
        when(mockGuard.getHealth()).thenReturn(10);
        when(mockPlayer.getPosition()).thenReturn(mockPosition);
        when(mockPosition.checkWall(any(), any())).thenReturn(false);
        when(mockPosition.viewAngle(any())).thenReturn(0.0);

        testViewer.draw(mockGUI);

        verify(mockGUI).clear();
        verify(mockGUI).drawFloor();
        verify(mockGUI).drawCeiling();
        verify(mockGUI).drawPlayerCamera(any(), any());
        verify(mockGUI).drawGuard(anyInt(), any(), any());
        verify(mockGUI).drawHearts();
        verify(mockGUI).drawAim();
        verify(mockGUI).drawGuardCounter();
        verify(mockGUI).refresh();
    }
}
