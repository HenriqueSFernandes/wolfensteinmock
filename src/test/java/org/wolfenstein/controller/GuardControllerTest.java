package org.wolfenstein.controller;

import org.junit.jupiter.api.Test;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Map;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.elements.Guard;
import org.wolfenstein.model.elements.Player;
import org.wolfenstein.model.sound.Sound;
import org.wolfenstein.model.sound.SoundLoader;

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
    Position mockPosition2 = mock(Position.class);
    SoundLoader mockSoundLoader = SoundLoader.getInstance();
    GuardController testController = new GuardController(mockCamera);

    @Test
    public void deadGuardTest() throws IOException {
        mockGuardList.clear();
        mockGuardList.add(mockGuard);
        when(mockCamera.getGuardList()).thenReturn(mockGuardList);
        when(mockGuard.getHealth()).thenReturn(0);
        mockSoundLoader.importSound("gun_shot.wav");
        mockSoundLoader.importSound("gun_shot.wav");

        testController.step(mockGame, GUI.GUIAction.NONE, 0);

        assertEquals(0, mockGuardList.size());
    }

    @Test
    public void fireTest() throws IOException {
        mockGuardList.clear();
        mockGuardList.add(mockGuard);
        when(mockCamera.getGuardList()).thenReturn(mockGuardList);
        when(mockGuard.getHealth()).thenReturn(20);
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPosition()).thenReturn(mockPosition);
        when(mockGuard.getPosition()).thenReturn(mockPosition2);
        when(mockPosition.checkWall(mockPosition, mockMap)).thenReturn(false);
        when(mockPosition.getAngle()).thenReturn(0.0);
        when(mockPosition2.getAngle()).thenReturn(180.0);

        testController.step(mockGame, GUI.GUIAction.FIRE, 0);

        verify(mockGuard).takeShot();
    }

    @Test
    public void aggroTest() throws IOException {
        mockGuardList.clear();
        mockGuardList.add(mockGuard);
        when(mockCamera.getGuardList()).thenReturn(mockGuardList);
        when(mockGuard.getHealth()).thenReturn(20);
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPosition()).thenReturn(mockPosition);
        when(mockGuard.getPosition()).thenReturn(mockPosition2);
        when(mockPosition.checkWall(mockPosition, mockMap)).thenReturn(false);
        when(mockGuard.isAggro()).thenReturn(false);

        testController.step(mockGame, GUI.GUIAction.FIRE, 0);

        verify(mockGuard).tick();
        verify(mockGuard).setAggro(true);
    }

    @Test
    public void moveBackwardTest() throws IOException {
        mockGuardList.clear();
        mockGuardList.add(mockGuard);
        when(mockCamera.getGuardList()).thenReturn(mockGuardList);
        when(mockGuard.getHealth()).thenReturn(20);
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPosition()).thenReturn(mockPosition);
        when(mockGuard.getPosition()).thenReturn(mockPosition2);
        when(mockPosition2.checkWall(mockPosition, mockMap)).thenReturn(false);
        when(mockGuard.isAggro()).thenReturn(true);

        testController.step(mockGame, GUI.GUIAction.FIRE, 0);

        verify(mockGuard).pointTo(mockPosition);
        verify(mockPosition2).moveBackwards();
    }

    @Test
    public void moveForwardTest() throws IOException {
        mockGuardList.clear();
        mockGuardList.add(mockGuard);
        when(mockCamera.getGuardList()).thenReturn(mockGuardList);
        when(mockGuard.getHealth()).thenReturn(20);
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPosition()).thenReturn(mockPosition);
        when(mockGuard.getPosition()).thenReturn(mockPosition2);
        when(mockGuard.isAggro()).thenReturn(true);
        when(mockPosition2.checkWall(mockPosition, mockMap)).thenReturn(true);
        when(mockPosition.getX()).thenReturn(0.0);
        when(mockPosition.getY()).thenReturn(0.0);
        when(mockPosition2.getX()).thenReturn(33.0);
        when(mockPosition2.getY()).thenReturn(0.0);

        testController.step(mockGame, GUI.GUIAction.FIRE, 0);

        verify(mockGuard).pointTo(mockPosition);
        verify(mockPosition2).moveForward();
    }

    @Test
    public void deAggroTest() throws IOException {
        mockGuardList.clear();
        mockGuardList.add(mockGuard);
        when(mockCamera.getGuardList()).thenReturn(mockGuardList);
        when(mockCamera.getMap()).thenReturn(mockMap);
        when(mockGuard.getHealth()).thenReturn(20);
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPosition()).thenReturn(mockPosition);
        when(mockGuard.getPosition()).thenReturn(mockPosition2);
        when(mockGuard.isAggro()).thenReturn(true);
        when(mockPosition2.checkWall(mockPosition, mockMap)).thenReturn(true);
        when(mockPosition.getX()).thenReturn(0.0);
        when(mockPosition.getY()).thenReturn(0.0);
        when(mockPosition2.getX()).thenReturn(30.0);
        when(mockPosition2.getY()).thenReturn(0.0);

        testController.step(mockGame, GUI.GUIAction.FIRE, 0);

        verify(mockGuard).pointTo(mockPosition);
        verify(mockGuard).setAggro(false);
    }

    @Test
    public void shotTest() throws IOException {
        mockGuardList.clear();
        mockGuardList.add(mockGuard);
        when(mockCamera.getGuardList()).thenReturn(mockGuardList);
        when(mockCamera.getMap()).thenReturn(mockMap);
        when(mockGuard.getHealth()).thenReturn(20);
        when(mockCamera.getPlayer()).thenReturn(mockPlayer);
        when(mockPlayer.getPosition()).thenReturn(mockPosition);
        when(mockGuard.getPosition()).thenReturn(mockPosition2);
        when(mockGuard.isAggro()).thenReturn(true);
        when(mockPosition2.checkWall(mockPosition, mockMap)).thenReturn(false);
        when(mockPosition.getX()).thenReturn(0.0);
        when(mockPosition.getY()).thenReturn(0.0);
        when(mockPosition2.getX()).thenReturn(28.0);
        when(mockPosition2.getY()).thenReturn(0.0);
        when(mockGuard.tick()).thenReturn(true);
        mockSoundLoader.importSound("gun_shot.wav");

        testController.step(mockGame, GUI.GUIAction.FIRE, 0);

        verify(mockGuard).pointTo(mockPosition);
        verify(mockPlayer).changeHealth(-1);
    }
}
