package org.wolfenstein.GUI;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Map;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
}
