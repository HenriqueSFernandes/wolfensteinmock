package org.wolfenstein.viewer;

import org.junit.jupiter.api.Test;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.model.Menu;
import org.wolfenstein.model.image.Image;
import org.wolfenstein.model.image.ImageLoader;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class MenuViewerTest {
    Menu mockMenu = mock(Menu.class);
    ImageLoader mockImageLoader = mock(ImageLoader.class);
    Image mockImage = mock(Image.class);
    GUI mockGUI = mock(GUI.class);
    MenuViewer testViewer = new MenuViewer(mockMenu, mockImageLoader);

    @Test
    public void drawStartTest() throws IOException {
        when(mockMenu.isSelectedStart()).thenReturn(true);
        when(mockMenu.isSelectedExit()).thenReturn(false);
        when(mockGUI.getGraphics()).thenReturn(null);
        when(mockImageLoader.getImage(11)).thenReturn(mockImage);

        testViewer.draw(mockGUI);

        verify(mockGUI).clear();
        verify(mockImage).draw(null);
        verify(mockGUI).refresh();
    }

    @Test
    public void drawExitTest() throws IOException {
        when(mockMenu.isSelectedStart()).thenReturn(false);
        when(mockMenu.isSelectedExit()).thenReturn(true);
        when(mockGUI.getGraphics()).thenReturn(null);
        when(mockImageLoader.getImage(12)).thenReturn(mockImage);

        testViewer.draw(mockGUI);

        verify(mockGUI).clear();
        verify(mockImage).draw(null);
        verify(mockGUI).refresh();
    }
}
