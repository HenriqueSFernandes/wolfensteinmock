package org.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LanternaScreen {
    private TerminalScreen screen;
    public void createScreen(int WIDTH, int HEIGHT) throws IOException, URISyntaxException, FontFormatException {
        URL resource = getClass().getClassLoader().getResource("square.ttf");
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        Font loadedFont = font.deriveFont(Font.PLAIN, 4);
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);
        factory.setTerminalEmulatorFontConfiguration(fontConfig);
        factory.setForceAWTOverSwing(true);
        factory.setInitialTerminalSize(new TerminalSize(WIDTH, HEIGHT));

        Terminal terminal = factory.createTerminal();
        ((AWTTerminalFrame) terminal).addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
        ((AWTTerminalFrame) terminal).setTitle("Wolfenstein Mock");
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
    }

    public void stopScreen() throws IOException {screen.close();}

    public KeyStroke readInput() throws IOException {return screen.readInput();}

    public TextGraphics newTextGraphics() {return screen.newTextGraphics();}

    public void clear() {screen.clear();}

    public void refresh() throws IOException {screen.refresh();}
}
