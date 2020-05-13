package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.workers.WorkerAtlas;
import it.polimi.ingsw.network.message.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {


    @BeforeEach
    void setUp(){
        new Map();
        new GameManager();
        new TurnManager();
    }

    @Test
    void workerChoiceTest(){
        new WorkerAtlas("Worker", 0, 0);    //? setWorker

        Player p = new Player("Player");
        p.setGodChoice(God.APOLLO); //?
        assertNull(p.getWorkerSelected(1));
        assertNull(p.getWorkerSelected(2));
        GameManager.getInstance().setCurrPlayer(p);

        GameManager.getInstance().setWorker(0, 0);
        GameManager.getInstance().setWorker(0, 1);
        TurnManager.getInstance().workerChoice(1);
        assertEquals(Game.getInstance().getGameState(p), GameState.MOVEMENT);

        Map.getInstance().setCellBlockType(0, 2, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(1, 0, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(1, 1, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(1, 2, BlockType.CUPOLA);
        TurnManager.getInstance().workerChoice(1);
        assertEquals(Game.getInstance().getGameState(p), GameState.MOVEMENT);

    }



}