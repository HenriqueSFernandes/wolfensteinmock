package org.wolfenstein.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class MenuTest {
    List<String> e = Arrays.asList("option1", "option2", "option3", "option4", "option5");
    Menu testMenu = new Menu(e);

    @Test
    void NumberEntriesTest() {
        assertEquals(5, testMenu.getNumberEntries());
    }
    Boolean[] selected = {true, false, false, false, false};
    @Test
    void isSelectedTest() {
        //isSelectedStart and isSelectedExit are just calls to the isSelected function set for a specific menu
        for (int i = 0; i < 5; i++) {
            assertEquals(selected[i], testMenu.isSelected(i));
        }
    }
    @Test
    void NextEntryTest() {
        //first entry (0) is selected by default
        int lastSelect = 0;
        isSelectedTest();
        //verify if nextEntry works correctly for the next entries
        for (int i = 1; i < 5; i++) {
            testMenu.nextEntry();
            selected[lastSelect] = false;
            selected[i] = true;
            lastSelect = i;
            isSelectedTest();
        }
        //verify that, when the last entry is the selected one, the next one is the first entry
        testMenu.nextEntry();
        selected[4] = false;
        selected[0] = true;
        isSelectedTest();
    }
    @Test
    void previousEntryTest() {
        int lastSelect = 0;
        isSelectedTest();
        //verify that, when the first entry is the selected one, the previous one is the last entry
        testMenu.previousEntry();
        selected[0] = false;
        selected[4] = true;
        lastSelect = 4;
        isSelectedTest();
        //verify if previousEntry works correctly for the previous entries
        for (int i = 3; i > -1; i--) {
            testMenu.previousEntry();
            selected[lastSelect] = false;
            selected[i] = true;
            lastSelect = i;
            isSelectedTest();
        }
    }
}
