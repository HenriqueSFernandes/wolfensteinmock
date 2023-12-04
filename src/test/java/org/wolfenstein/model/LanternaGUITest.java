package org.wolfenstein.model;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.GUI.LanternaGUI;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.wolfenstein.GUI.LanternaGUI.*;

public class LanternaGUITest {
    TerminalScreen mockScreen;
    TextGraphics mockGraphics;
    LanternaGUI mockGUI;
    public LanternaGUITest() throws IOException {
        mockScreen = mock(TerminalScreen.class);
        mockGraphics = mock(TextGraphics.class);
        mockGUI = new LanternaGUI(mockScreen, mockGraphics);
        when(mockScreen.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowUp)).thenReturn(null);
        when(mockGraphics.putString(anyInt(), anyInt(), anyString())).thenReturn(mockGraphics);
        when(mockGraphics.setBackgroundColor(GRAY)).thenReturn(mockGraphics);
        verify(mockScreen, times(1)).startScreen();
    }
    @Test
    void getNextActionTest() throws IOException {
        GUI.GUIAction testAction = mockGUI.getNextAction();
        assertEquals(GUI.GUIAction.FRONT, testAction);
    }
    @Test
    void drawMapTest() {
        Map mockMap = mock(Map.class);
        when(mockMap.getMap()).thenReturn(new int[][] {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}});
        when(mockMap.getCellsize()).thenReturn(3);
        when(mockMap.getWidth()).thenReturn(3);
        when(mockMap.getHeight()).thenReturn(3);
        mockGUI.drawMap(mockMap);
        verify(mockGraphics, times(73)).setBackgroundColor(GRAY);
        verify(mockGraphics, times(8)).setBackgroundColor(BLACK);
        verify(mockGraphics, times(1)).setBackgroundColor(WHITE);
        verify(mockGraphics, times(1)).fillRectangle(new TerminalPosition(0, 0), new TerminalSize(3 * 2, 3), ' ');
        verify(mockGraphics, times(81)).putString(anyInt(), anyInt(),  eq(" "));
    }
    @Test
    void drawText() {
        mockGUI.drawText(10, 10, "Menu");
        verify(mockGraphics, times(1)).putString(10, 10, "Menu");
    }
    @Test
    void drawPlayerCameraTest() {

    }
}
