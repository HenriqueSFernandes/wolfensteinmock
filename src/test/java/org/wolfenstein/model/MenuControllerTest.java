package org.wolfenstein.model;

import net.jqwik.api.*;
import org.mockito.Mockito;
import org.wolfenstein.GUI.GUI;
import org.wolfenstein.Game;
import org.wolfenstein.controller.MenuController;

import java.util.List;

public class MenuControllerTest {
    Menu mockMenu = Mockito.mock(Menu.class);
    Game mockGame = Mockito.mock(Game.class);
    MenuController testCtrl = new MenuController(mockMenu);
    @Provide
    Arbitrary<GUI.GUIAction> actions() {
        return Arbitraries.of(GUI.GUIAction.FRONT, GUI.GUIAction.BACK, GUI.GUIAction.SELECT);
    }
    @Property
    void stepTest(@ForAll List<GUI.@From("actions") GUIAction> act) {
        for(GUI.GUIAction a : act) {
            switch (a) {
                case FRONT:
                    Mockito.when(mockMenu.isSelectedExit()).thenReturn(false);
                    Mockito.when(mockMenu.isSelectedStart()).thenReturn(false);
                    Mockito.doNothing().when(mockMenu).previousEntry();
                    break;
                case BACK:
                    Mockito.when(mockMenu.isSelectedExit()).thenReturn(false);
                    Mockito.when(mockMenu.isSelectedStart()).thenReturn(false);
                    Mockito.doNothing().when(mockMenu).nextEntry();
                    break;
                case SELECT:
                    Mockito.when(mockMenu.isSelectedExit()).thenReturn(false);
                    Mockito.when(mockMenu.isSelectedStart()).thenReturn(false);
                    break;
            }
            testCtrl.step(mockGame, a, 1);
            switch (a) {
                case FRONT:
                    Mockito.verify(mockMenu, Mockito.times(1)).previousEntry();
                    break;
                case BACK:
                    Mockito.verify(mockMenu, Mockito.times(1)).nextEntry();
                    break;
                case SELECT:
                    Mockito.verify(mockMenu, Mockito.times(1)).isSelectedExit();
                    Mockito.verify(mockMenu, Mockito.times(1)).isSelectedStart();
                    break;
            }
        }
    }
}
