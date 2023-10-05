package com.bol.kalah.service;


import com.bol.kalah.model.KalahGame;

/**
 * This interface created for Service Implementation
 *
 * @author revathik
 */
public interface KalahGameService {

    /**
     * To create a new game
     * @param initialStoneOnPit pit number
     * @return KalahGame object
     */
    KalahGame createGame(Integer initialStoneOnPit);

    /**
     * To play a game
     * @param gameId as Game Id
     * @param pitId as pit index
     * @return KalahGame object
     */
    KalahGame play(String gameId, Integer pitId);
}

