package it.polimi.ingsw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionManagerTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void isAroundTest(){
        assertFalse(ActionManager.getInstance().isAround(0, 0, 4, 4));
        assertFalse(ActionManager.getInstance().isAround(0, 0, 1, 4));
        assertTrue(ActionManager.getInstance().isAround(0, 0, 1, 1));
        assertFalse(ActionManager.getInstance().isAround(2, 2, 4, 4));
        assertTrue(ActionManager.getInstance().isAround(2, 2, 3, 3));
        assertFalse(ActionManager.getInstance().isAround(0, 1, 4, 4));
        assertFalse(ActionManager.getInstance().isAround(2, 0, 1, 4));
        assertTrue(ActionManager.getInstance().isAround(1, 3, 1, 2));
        assertFalse(ActionManager.getInstance().isAround(4, 0, 0, 4));
        assertTrue(ActionManager.getInstance().isAround(4, 1, 3, 1));
    }

    @Test
    void validCoordsTest(){
        assertFalse(ActionManager.getInstance().validCoords(5, 5));
        assertFalse(ActionManager.getInstance().validCoords(5, 0));
        assertFalse(ActionManager.getInstance().validCoords(0, 5));
        assertTrue(ActionManager.getInstance().validCoords(0, 0));
        assertTrue(ActionManager.getInstance().validCoords(0, 4));
        assertTrue(ActionManager.getInstance().validCoords(2, 3));
        assertFalse(ActionManager.getInstance().validCoords(15, 8));
        assertFalse(ActionManager.getInstance().validCoords(7, 0));
        assertFalse(ActionManager.getInstance().validCoords(0, 7));
        assertTrue(ActionManager.getInstance().validCoords(3, 2));
        assertTrue(ActionManager.getInstance().validCoords(1, 3));
        assertTrue(ActionManager.getInstance().validCoords(4, 4));
    }

    @Test
    void verifyCoordinateMovementTest(){

    }

    @Test
    void verifyCoordinateConstructionTest(){

    }
}