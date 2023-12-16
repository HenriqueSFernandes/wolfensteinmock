package org.wolfenstein.controller;

import org.junit.jupiter.api.Test;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.model.Menu;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MenuControllerTest {
    @Test
    public void constructorTest() {
        Menu mockMenu = mock(Menu.class);
        MenuController testController = new MenuController(mockMenu);

        assertEquals(mockMenu, testController.getModel());
    }

    @Test
    public void actionsTest() throws IOException {
        Menu mockMenu = mock(Menu.class);
        Game mockGame = mock(Game.class);
        MenuController testController = new MenuController(mockMenu);
        when(mockMenu.isSelectedExit()).thenReturn(true);

        testController.step(mockGame, GUI.GUIAction.BACK, 0);

        verify(mockMenu).nextEntry();

        testController.step(mockGame, GUI.GUIAction.FRONT, 0);

        verify(mockMenu).previousEntry();

        testController.step(mockGame, GUI.GUIAction.SELECT, 0);

        verify(mockGame).setState(null);
    }
}
