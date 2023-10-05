package com.bol.kalah.helper;


import com.bol.kalah.model.KalahGame;
import com.bol.kalah.model.KalahGamePit;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represent for distribute to the next pits
 * except for the opponent home.
 *
 * @author revathik
 */
@Slf4j
public class DistributePitStoneRule extends KalahGameRule {

    @Override
    public void apply(KalahGame game, KalahGamePit currentPit) {
        log.debug("check the rules for distributing stone to the next pit(s)");
        KalahGamePit currentPitData = currentPit;
        Integer stoneToDistribute = currentPitData.getStoneCount();
        currentPitData.setStoneCount(0);
        for (int i = 0; i < stoneToDistribute; i++) {
            currentPitData = game.getBoard().getNextPit(currentPitData);
            log.debug("next pit {}", currentPitData);
            if (currentPitData.isDistributable(game.getGameStatus())) {
                currentPitData.setStoneCount(currentPitData.getStoneCount() + 1);
            }else{
                i--;
            }
        }
        this.next.apply(game, currentPitData);
    }
}
