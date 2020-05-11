package it.polimi.ingsw.network.message;

public enum GameState {

    WELCOME_FIRST,
    WAIT_PLAYERS, WAIT_CARD_CHOICE, WAIT_TURN,                          WAIT, LOAD_STATE, SET_WORKER1, SET_WORKER2,
    CARD_CHOICE, SET_WORKER,
    WORKER_CHOICE,
    QUESTION_PROMETHEUS, PREBUILD_PROMETHEUS,
    MOVEMENT,
    QUESTION_ARTEMIS, SECOND_MOVE,
    QUESTION_TRITON,
    QUESTION_ATLAS, CONSTRUCTION_CUPOLA,
    QUESTION_HEPHAESTUS, DOUBLE_CONSTRUCTION,
    QUESTION_DEMETER, SECOND_CONSTRUCTION_DEMETER,
    QUESTION_HESTIA, SECOND_CONSTRUCTION_HESTIA,
    CONSTRUCTION,
    ERROR

}
