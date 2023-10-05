package com.bol.kalah.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the kalah game which has players,game status and board
 *
 * @author revathik
 */
@Data
@NoArgsConstructor
public class KalahGame {

    /**
     * Holds the kalah game id
     */
    private String id;
    /**
     * Holds the kalah game board object
     */
    private KalahGameBoard board;
    /**
     * Holds the kalah game player1 object
     */
    private KalahGamePlayer player1;
    /**
     * Holds the kalah game player2 object
     */
    private KalahGamePlayer player2;
    /**
     * Holds the winner info
     */
    private KalahGamePlayer winner;
    /**
     * Holds the status of the game
     */
    private KalahGameStatus gameStatus;

    /**
     * Invoke to start the kalah game
     * @param initialStoneOnPit
     */
    public KalahGame(Integer initialStoneOnPit) {
        this.player1 = new KalahGamePlayer(KalahGamePlayer.PLAYER1_INDEX, "player1");
        this.player2 = new KalahGamePlayer(KalahGamePlayer.PLAYER2_INDEX, "player2");
        this.board = new KalahGameBoard(initialStoneOnPit, player1, player2);
        this.gameStatus = KalahGameStatus.INIT;
    }
}