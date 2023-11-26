package org.wolfenstein.model;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Positive;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LanternaGUITest {
    Terminal mockTerminal = Mockito.mock(Terminal.class);
    TerminalScreen mockScreen = new TerminalScreen(mockTerminal);
    public LanternaGUITest() throws IOException {mockScreen.close();}
    @Test
    void createScreenTest() {

    }
    @Test
    void getNextActionTest() {

    }
    @Test
    void drawMapTest() {

    }
    @Test
    void drawText() {

    }
    @Test
    void drawPlayerCameraTest() {

    }
    @Test
    void createLineTest() {

    }
}
