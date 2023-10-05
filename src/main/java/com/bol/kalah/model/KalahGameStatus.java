package com.bol.kalah.model;

/**
 * Identifies the current kalah game status
 *
 * @author revathik
 */
public enum KalahGameStatus {

    /**
     * Kalah Game initiated
     */
    INIT,

    /**
     * Player 1 is on the turn
     */
    PLAYER1_TURN,

    /**
     * Player 2 is on the turn
     */
    PLAYER2_TURN,

    /**
     * Kalah game has finished
     */
    GAMEOVER
}
