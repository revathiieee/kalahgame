package com.bol.kalah.helper;

import com.bol.kalah.model.KalahGame;
import com.bol.kalah.model.KalahGamePit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * This class represent the kalah game rule
 *
 * @author revathik
 */
@Slf4j
@Component
public class KalahGameHelper {

    /**
     * Instanstiates gamerule
     */
    private final KalahGameRule gameRule;

    /**
     * Default Constructor
     */
    public KalahGameHelper() {
        this.gameRule = new StartPitRule();
        gameRule.setNext(new DistributePitStoneRule())
                .setNext(new EndPitRule())
                .setNext(new GameOverRule());
    }

    /**
     * Rule to play the game
     * @param game KalahGame Object
     * @param pit KalahGamePit Object
     */
    public void play(KalahGame game, KalahGamePit pit) {
        this.gameRule.apply(game, pit);
    }
}
