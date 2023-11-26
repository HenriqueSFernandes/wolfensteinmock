package org.wolfenstein.model;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.controller.MenuController;
import org.wolfenstein.state.GameState;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MenuControllerTest {
    Menu mockMenu = mock(Menu.class);
    Game mockGame = mock(Game.class);
    MenuController mockController = new MenuController(mockMenu);

    @Test
    public void exitTest() {
        when(mockMenu.isSelectedExit()).thenReturn(true);
        mockController.step(mockGame, GUI.GUIAction.SELECT, 0);
        verify(mockGame, times(1)).setState(null);
    }
    @Test
    public void enterTest() {
        when(mockMenu.isSelectedStart()).thenReturn(true);
        mockController.step(mockGame, GUI.GUIAction.SELECT, 0);
        verify(mockGame, times(1)).setState(any());
    }
    @Test
    public void moveTest() {
        Menu menu = new Menu();
        MenuController menuController = new MenuController(menu);
        boolean selectedStart = menu.isSelectedStart();
        assertTrue(selectedStart);
        menuController.step(mockGame, GUI.GUIAction.BACK, 0);
        boolean selectedExit = menu.isSelectedExit();
        assertTrue(selectedExit);
        menuController.step(mockGame, GUI.GUIAction.FRONT, 0);
        selectedStart = menu.isSelectedStart();
        assertTrue(selectedStart);
    }
}
