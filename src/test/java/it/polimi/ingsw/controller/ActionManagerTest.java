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
    void verifyCoordinateMovement_OccupiedCellApolloTest2(){//lavoratore avversario e potere di athena senza salire
        new WorkerDemeter("BLU1", 1, 1);
        TurnManager.getInstance().setAllowHeight(false);
        assertTrue(ActionManager.getInstance().verifyCoordinateMovement(new WorkerApollo("RED2", 0, 0), God.APOLLO, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellApolloTest3(){//lavoratore avversario e potere di athena e salire
        new WorkerDemeter("BLU1", 1, 1);
        TurnManager.getInstance().setAllowHeight(false);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerApollo("RED2", 0, 0), God.APOLLO, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellApolloTest4(){//lavoratore avversario e salire di un livello
        new WorkerDemeter("BLU1", 1, 1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertTrue(ActionManager.getInstance().verifyCoordinateMovement(new WorkerApollo("RED2", 0, 0), God.APOLLO, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellApolloTest5(){//lavoratore avversario e salire di due o più livelli
        new WorkerDemeter("BLU1", 1, 1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK2);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerApollo("RED2", 0, 0), God.APOLLO, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellMinotaurTest1(){//tuo lavoratore
        new WorkerMinotaur("RED1", 1, 1);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerMinotaur("RED2", 0, 0), God.MINOTAUR, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellMinotaurTest2(){//lavoratore avversario e potere di athena senza salire e puoi spingere
        new WorkerDemeter("BLU1", 1, 1);
        TurnManager.getInstance().setAllowHeight(false);
        assertTrue(ActionManager.getInstance().verifyCoordinateMovement(new WorkerMinotaur("RED2", 0, 0), God.MINOTAUR, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellMinotaurTest3(){//lavoratore avversario e potere di athena senza salire e non puoi spingere
        new WorkerDemeter("BLU1", 1, 1);
        new WorkerDemeter("BLU2", 2, 2);
        TurnManager.getInstance().setAllowHeight(false);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerMinotaur("RED2", 0, 0), God.MINOTAUR, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellMinotaurTest4(){//lavoratore avversario e potere di athena e salire
        new WorkerDemeter("BLU1", 1, 1);
        TurnManager.getInstance().setAllowHeight(false);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerMinotaur("RED2", 0, 0), God.MINOTAUR, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellMinotaurTest5(){//lavoratore avversario e salire di un livello e puoi spingere
        new WorkerDemeter("BLU1", 1, 1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertTrue(ActionManager.getInstance().verifyCoordinateMovement(new WorkerMinotaur("RED2", 0, 0), God.MINOTAUR, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellMinotaurTest6(){//lavoratore avversario e salire di un livello e non puoi spingere
        new WorkerDemeter("BLU1", 1, 1);
        new WorkerDemeter("BLU2", 2, 2);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerMinotaur("RED2", 0, 0), God.MINOTAUR, 1, 1));
    }

    @Test
    void verifyCoordinateMovement_OccupiedCellMinotaurTest7(){//lavoratore avversario e salire di due o più livelli
        new WorkerDemeter("BLU1", 1, 1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK2);
        assertFalse(ActionManager.getInstance().verifyCoordinateMovement(new WorkerMinotaur("RED2", 0, 0), God.MINOTAUR, 1, 1));
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
    void verifyCoordinateConstruction_IrregularCoordinatesTest(){
        assertFalse(ActionManager.getInstance().verifyCoordinateConstruction(new WorkerAtlas("W", 0, 0), God.ATLAS, false, 5, 5));
    }

    @Test
    void verifyCoordinateConstruction_NotAroundCoordinatesTest(){
        assertFalse(ActionManager.getInstance().verifyCoordinateConstruction(new WorkerAtlas("W", 0, 0), God.ATLAS, false, 3, 3));
    }

    @Test
    void verifyCoordinateConstruction_ActualPositionTest(){
        assertFalse(ActionManager.getInstance().verifyCoordinateConstruction(new WorkerAtlas("W", 0, 0), God.ATLAS, false, 0, 0));
    }

    @Test
    void verifyCoordinateConstruction_ActualPositionZeusTest1(){//livello attuale minore di 3
        assertTrue(ActionManager.getInstance().verifyCoordinateConstruction(new WorkerZeus("W", 0, 0), God.ZEUS, false, 0, 0));
    }

    @Test
    void verifyCoordinateConstruction_ActualPositionZeusTest2(){//livello uguale a 3
        Map.getInstance().setCellBlockType(0, 0, BlockType.BLOCK3);
        assertFalse(ActionManager.getInstance().verifyCoordinateConstruction(new WorkerZeus("W", 0, 0), God.ZEUS, false, 0, 0));
    }

    @Test
    void verifyCoordinateConstruction_OccupiedCellTest(){
        new WorkerDemeter("W1", 1, 1);
        assertFalse(ActionManager.getInstance().verifyCoordinateConstruction(new WorkerAtlas("W", 0, 0), God.ATLAS, false, 1, 1));
    }

    @Test
    void verifyCoordinateConstruction_CupolaTest(){
        Map.getInstance().setCellBlockType(1, 1, BlockType.CUPOLA);
        assertFalse(ActionManager.getInstance().verifyCoordinateConstruction(new WorkerAtlas("W", 0, 0), God.ATLAS, false, 1, 1));
    }

    @Test
    void verifyCoordinateConstruction_Test(){
        assertTrue(ActionManager.getInstance().verifyCoordinateConstruction(new WorkerAtlas("W", 0, 0), God.ATLAS, false, 1, 1));
    }

    @Test
    void verifyCoordinateConstruction_DoubleConstructionTest(){//blocco di livello 0 o 1
        assertTrue(ActionManager.getInstance().verifyCoordinateConstruction(new WorkerAtlas("W", 0, 0), God.ATLAS, true, 1, 1));
    }

    @Test
    void verifyCoordinateConstruction_DoubleConstructionTest2(){//blocco di livello 2 o 3
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK2);
        assertFalse(ActionManager.getInstance().verifyCoordinateConstruction(new WorkerAtlas("W", 0, 0), God.ATLAS, true, 1, 1));
    }

}