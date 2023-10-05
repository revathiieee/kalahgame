package com.bol.kalah.service.impl;


import com.bol.kalah.helper.KalahGameHelper;
import com.bol.kalah.model.KalahGame;
import com.bol.kalah.model.KalahGamePit;
import com.bol.kalah.repository.KalahGameRepository;
import com.bol.kalah.service.KalahGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Implementation of KalahGameService Interface
 *
 * @author revathik
 */
@Slf4j
@Service
public class KalahGameServiceImpl implements KalahGameService {

    /**
     * Instantiaties GameRepository
     */
    @Autowired
    private KalahGameRepository gameRepository;

    /**
     * Instantiaties GameHelper
     */
    @Autowired
    private KalahGameHelper gameHelper;

    /**
     * Method to start the new kalah game
     * @param initialStoneOnPit initial number of stone
     * @return KalahGame object
     */
    @Override
    public KalahGame createGame(Integer initialStoneOnPit) {
        return gameRepository.createKalahGame(initialStoneOnPit);
    }

    /**
     * Method is responsible for every new move of the stones from a pit.
     * @param gameId Kalah Game Id
     * @param pitId Index of the pit
     * @return KalahGame object
     */
    @Override
    public KalahGame play(String gameId, Integer pitId) {
        log.debug("gameId {}, pitIndex {}",gameId, pitId);
        KalahGame game = gameRepository.findByKalahGameId(gameId);
        KalahGamePit pit = game.getBoard().getPitByPitIndex(pitId);
        gameHelper.play(game, pit);
        return game;
    }
}
