package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.workers.*;
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
    void verifyCoordinateMovement_IrregularCoordinatesTest(){
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAtlas("W", 0, 0), God.ATLAS, 5, 5));
    }

    @Test
    void verifyCoordinateMovement_NotAroundCoordinatesTest(){
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAtlas("W", 0, 0), God.ATLAS, 3, 3));
    }

    @Test
    void verifyCoordinateMovement_ActualPositionTest(){
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAtlas("W", 0, 0), God.ATLAS, 0, 0));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellTest(){
        new WorkerDemeter("W1", 1, 1);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAtlas("W", 0, 0), God.ATLAS, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellApolloTest1(){//tuo lavoratore
        new WorkerApollo("RED1", 1, 1);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerApollo("RED2", 0, 0), God.APOLLO, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_CupolaTest(){
        Map.getInstance().setCellBlockType(1, 1, BlockType.CUPOLA);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAtlas("W", 0, 0), God.ATLAS, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_PowerAthenaTest1(){//senza salire
        TurnManager.getInstance().setAllowHeight(false);
        assertTrue(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAtlas("W", 0, 0), God.ATLAS, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_PowerAthenaTest2(){//salire ma sei Athena
        TurnManager.getInstance().setAllowHeight(false);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertTrue(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAthena("W", 0, 0), God.ATHENA, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_PowerAthenaTest3(){//salire e non sei athena
        TurnManager.getInstance().setAllowHeight(false);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAtlas("W", 0, 0), God.ATLAS, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_PowerPrometheusTest1(){//senza salire
        TurnManager.getInstance().setAllowHeightPrometheus(false);
        assertTrue(ActionManager.getInstance().verifyCoordinateMovement(new WorkerPrometheus("W", 0, 0), God.PROMETHEUS, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_PowerPrometheusTest2(){//salire
        TurnManager.getInstance().setAllowHeightPrometheus(false);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerPrometheus("W", 0, 0), God.PROMETHEUS, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_NoGoUpTest(){
        assertTrue(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAtlas("W", 0, 0), God.ATLAS, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_SingleGoUpTest(){
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertTrue(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAtlas("W", 0, 0), God.ATLAS, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_DoubleGoUpTest(){
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK2);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerAtlas("W", 0, 0), God.ATLAS, 1, 1));
    }

    @Test
    void verifyCoordinateConstructionTest(){

    }
}