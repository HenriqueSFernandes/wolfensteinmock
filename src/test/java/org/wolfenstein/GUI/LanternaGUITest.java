package org.wolfenstein.GUI;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Camera;
import org.wolfenstein.model.Map;
import org.wolfenstein.model.Position;
import org.wolfenstein.model.image.Animation;
import org.wolfenstein.model.image.AnimationLoader;
import org.wolfenstein.model.image.ImageLoader;
import org.wolfenstein.model.image.Image;
import org.wolfenstein.model.sound.Sound;
import org.wolfenstein.model.sound.SoundLoader;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.wolfenstein.GUI.LanternaGUI.*;

public class LanternaGUITest {
    TextGraphics mockGraphics = mock(TextGraphics.class);
    AnimationLoader mockAnimationLoader = mock(AnimationLoader.class);
    SoundLoader mockSoundLoader = mock(SoundLoader.class);
    Sound mockSound = mock(Sound.class);
    Animation mockAnimation = mock(Animation.class);
    Image mockImage = mock(Image.class);
    ImageLoader mockImageLoader = mock(ImageLoader.class);
    TerminalScreen mockScreen = mock(TerminalScreen.class);
    KeyStroke mockKey = mock(KeyStroke.class);
    Map mockMap = mock(Map.class);
    Position mockPosition = mock(Position.class);
    LanternaGUI testGUI = new LanternaGUI(mockScreen, mockGraphics, mockAnimationLoader, mockSoundLoader, mockImageLoader);;


    @Test
    public void constructorTest() throws IOException, URISyntaxException, FontFormatException {
        LanternaGUI testGUI = new LanternaGUI(120, 180);
        testGUI.stopScreen();
    }

    @Test
    public void screenTest() throws IOException {
        testGUI.clear();
        testGUI.refresh();
        testGUI.stopScreen();

        verify(mockScreen).close();
        verify(mockScreen).refresh();
        verify(mockScreen).clear();
        assertEquals(mockGraphics, testGUI.getGraphics());
    }

    @Test
    public void getNextActionTest() throws IOException {
        when(mockScreen.pollInput()).thenReturn(null);

        assertEquals(GUI.GUIAction.NONE, testGUI.getNextAction());

        when(mockScreen.pollInput()).thenReturn(mockKey).thenReturn(null);
        when(mockKey.getKeyType()).thenReturn(KeyType.EOF);

        assertEquals(GUI.GUIAction.QUIT, testGUI.getNextAction());

        when(mockScreen.pollInput()).thenReturn(mockKey).thenReturn(null);
        when(mockKey.getKeyType()).thenReturn(KeyType.Character);
        when(mockKey.getCharacter()).thenReturn('q');

        assertEquals(GUI.GUIAction.QUIT, testGUI.getNextAction());

        when(mockScreen.pollInput()).thenReturn(mockKey).thenReturn(null);
        when(mockKey.getKeyType()).thenReturn(KeyType.Character);
        when(mockKey.getCharacter()).thenReturn('w');

        assertEquals(GUI.GUIAction.FRONT, testGUI.getNextAction());

        when(mockScreen.pollInput()).thenReturn(mockKey).thenReturn(null);
        when(mockKey.getKeyType()).thenReturn(KeyType.Character);
        when(mockKey.getCharacter()).thenReturn('a');

        assertEquals(GUI.GUIAction.LEFT, testGUI.getNextAction());

        when(mockScreen.pollInput()).thenReturn(mockKey).thenReturn(null);
        when(mockKey.getKeyType()).thenReturn(KeyType.Character);
        when(mockKey.getCharacter()).thenReturn('s');

        assertEquals(GUI.GUIAction.BACK, testGUI.getNextAction());

        when(mockScreen.pollInput()).thenReturn(mockKey).thenReturn(null);
        when(mockKey.getKeyType()).thenReturn(KeyType.Character);
        when(mockKey.getCharacter()).thenReturn('d');

        assertEquals(GUI.GUIAction.RIGHT, testGUI.getNextAction());

        when(mockScreen.pollInput()).thenReturn(mockKey).thenReturn(null);
        when(mockKey.getKeyType()).thenReturn(KeyType.Character);
        when(mockKey.getCharacter()).thenReturn('e');

        assertEquals(GUI.GUIAction.SELECT, testGUI.getNextAction());

        when(mockScreen.pollInput()).thenReturn(mockKey).thenReturn(null);
        when(mockKey.getKeyType()).thenReturn(KeyType.Character);
        when(mockKey.getCharacter()).thenReturn('p');

        assertEquals(GUI.GUIAction.SKIP, testGUI.getNextAction());

        when(mockScreen.pollInput()).thenReturn(mockKey).thenReturn(null);
        when(mockKey.getKeyType()).thenReturn(KeyType.Character);
        when(mockKey.getCharacter()).thenReturn('v');

        assertEquals(GUI.GUIAction.NONE, testGUI.getNextAction());

        when(mockScreen.pollInput()).thenReturn(mockKey).thenReturn(null);
        when(mockKey.getKeyType()).thenReturn(KeyType.Character);
        when(mockKey.getCharacter()).thenReturn(' ');
        when(mockAnimationLoader.getAnimation(0)).thenReturn(mockAnimation);
        List<Image> mockSprite = new ArrayList<>();
        mockSprite.add(mockImage);
        when(mockAnimation.getAnimation()).thenReturn(mockSprite);
        when(mockAnimation.getCurrentFrame()).thenReturn(mockImage);
        when(mockSoundLoader.getSound(0)).thenReturn(mockSound);

        assertEquals(GUI.GUIAction.FIRE, testGUI.getNextAction());
    }

    @Test
    public void drawMapTest() {
        List<List<Integer>> mockGrid = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 0, 5), Arrays.asList(6, 1, 1));
        when(mockMap.getMap()).thenReturn(mockGrid);
        when(mockMap.getHeight()).thenReturn(3);
        when(mockMap.getWidth()).thenReturn(3);
        when(mockMap.getCellsize()).thenReturn(3);

        testGUI.drawMap(mockMap);

        verify(mockGraphics, times(73)).setBackgroundColor(GRAY);
        verify(mockGraphics).fillRectangle(any(), any(), eq(' '));
        verify(mockGraphics, times(4)).setBackgroundColor(BLACK);
        verify(mockGraphics, times(1)).setBackgroundColor(new TextColor.RGB(0, 255, 0));
        verify(mockGraphics, times(1)).setBackgroundColor(new TextColor.RGB(0, 255, 255));
        verify(mockGraphics, times(1)).setBackgroundColor(new TextColor.RGB(255, 255, 0));
        verify(mockGraphics, times(1)).setBackgroundColor(new TextColor.RGB(255, 0, 0));
        verify(mockGraphics, times(1)).setBackgroundColor(WHITE);
        verify(mockGraphics, times(81)).putString(anyInt(), anyInt(), eq(" "));
    }

    @Test
    public void drawTextTest() {
        testGUI.drawText(0, 0, "test");

        verify(mockGraphics).setBackgroundColor(BLACK);
        verify(mockGraphics).putString(0, 0, "test");
    }

    @Test
    public void drawPlayerCameraTest() throws IOException {
        List<List<Integer>> mockGrid = Arrays.asList(Arrays.asList(1, 1, 1), Arrays.asList(4, 0, 1), Arrays.asList(1, 1, 1));
        List<Position> mockLine = Arrays.asList(mockPosition);
        List<Position> mockDoorLine = Arrays.asList(mockPosition);
        when(mockMap.getMap()).thenReturn(mockGrid);
        when(mockMap.getHeight()).thenReturn(3);
        when(mockMap.getWidth()).thenReturn(3);
        when(mockMap.getCellsize()).thenReturn(3);
        when(mockPosition.getRayAngle(eq(mockMap), anyInt())).thenReturn(0.0);
        when(mockPosition.getAngle()).thenReturn(0.0);
        when(mockPosition.createLine(0.0, mockMap)).thenReturn(mockLine);
        when(mockPosition.createLineForDoor(0.0, mockMap)).thenReturn(mockDoorLine);

        testGUI.drawPlayerCamera(mockPosition, mockMap);

        verify(mockAnimationLoader).drawAllAnimations(mockGraphics);
        verify(mockGraphics, times(18)).setCharacter(anyInt(), anyInt(), eq(' '));
        verify(mockGraphics, times(27)).setBackgroundColor(any());
        verify(mockGraphics, times(18)).drawLine(anyInt(), anyInt(), anyInt(), anyInt(), eq(' '));
    }

    @Test
    public void drawFloorTest() {
        when(mockGraphics.getSize()).thenReturn(new TerminalSize(10, 10));

        testGUI.drawFloor();

        verify(mockGraphics).getSize();
        verify(mockGraphics).setBackgroundColor(BROWN);
        verify(mockGraphics).fillRectangle(any(), any(), eq(' '));
    }

    @Test
    public void drawCeilingTest() {
        when(mockGraphics.getSize()).thenReturn(new TerminalSize(10, 10));

        testGUI.drawCeiling();

        verify(mockGraphics).getSize();
        verify(mockGraphics).setBackgroundColor(BLUE);
        verify(mockGraphics).fillRectangle(any(), any(), eq(' '));
    }

    @Test
    public void drawHeartsTest() {
        when(mockImageLoader.getImage(anyInt())).thenReturn(mockImage);

        testGUI.drawHearts();

        verify(mockImage, times(10)).draw(mockGraphics);
    }

    @Test
    public void drawAimTest() {
        when(mockImageLoader.getImage(10)).thenReturn(mockImage);

        testGUI.drawAim();

        verify(mockImage).draw(mockGraphics);
    }

    @Test
    public void drawGuardTest() throws IOException {
        when(mockImageLoader.getImage(13)).thenReturn(mockImage);
        List<List<Integer>> mockGrid = Arrays.asList(Arrays.asList(1, 1, 1), Arrays.asList(4, 0, 1), Arrays.asList(1, 1, 1));
        List<Position> mockLine = Arrays.asList(mockPosition);
        List<Position> mockDoorLine = Arrays.asList(mockPosition, mockPosition);
        when(mockMap.getMap()).thenReturn(mockGrid);
        when(mockMap.getHeight()).thenReturn(3);
        when(mockMap.getWidth()).thenReturn(3);
        when(mockMap.getCellsize()).thenReturn(3);
        when(mockPosition.getRayAngle(eq(mockMap), anyInt())).thenReturn(0.0);
        when(mockPosition.getAngle()).thenReturn(0.0);
        when(mockPosition.createLine(0.0, mockMap)).thenReturn(mockLine);
        when(mockPosition.createLineForDoor(0.0, mockMap)).thenReturn(mockDoorLine);

        testGUI.drawGuard(0, mockPosition, mockMap);

        verify(mockGraphics, times(9)).setBackgroundColor(new TextColor.RGB(255, 0, 0));
        verify(mockGraphics, times(9)).setCharacter(anyInt(), anyInt(), eq(' '));
        verify(mockGraphics, times(18)).setCharacter(anyInt(), anyInt(), eq('@'));
        verify(mockImage).draw(mockGraphics);
        verify(mockImage).setPosition(any());
    }

    @Test
    public void drawGuardCounterTest() throws IOException {
        Camera mockCamera = Camera.createCamera();
        mockCamera.createGuardList();
        when(mockImageLoader.getImage(anyInt())).thenReturn(mockImage);

        testGUI.drawGuardCounter();

        verify(mockImage, times(9)).draw(mockGraphics);

    }
}
