package org.wolfenstein.state;

import org.junit.jupiter.api.Test;
import org.wolfenstein.model.Camera;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GameStateTest {
    Camera mockCamera = mock(Camera.class);
    GameState testState = new GameState(mockCamera);

    @Test
    public void stateTest() {
        assertEquals(mockCamera, testState.getModel());
        assertEquals(mockCamera, testState.getController().getModel());
        assertEquals(mockCamera, testState.getViewer().getModel());
    }
}
