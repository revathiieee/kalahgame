package com.bol.kalah.helper;


import com.bol.kalah.model.KalahGame;
import com.bol.kalah.model.KalahGamePit;
import com.bol.kalah.model.KalahGameStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represent to check the last stone placing rule
 *
 * @author revathik
 */
@Slf4j
public class EndPitRule extends KalahGameRule {

    @Override
    public void apply(KalahGame game, KalahGamePit endPit) {
        log.debug("checking end rule for the last pit {}", endPit);
        lastEmptyPitRule(game, endPit);
        nextPlayerTurnRule(game, endPit);
        this.next.apply(game, endPit);
    }

    /**
     * Check empty for last pit rule
     * @param game as an object
     * @param endPit as an object
     */
    private void lastEmptyPitRule(KalahGame game, KalahGamePit endPit){
        if (!endPit.isHome() && endPit.isPlayerPit(game.getGameStatus()) && endPit.getStoneCount().equals(1) ){
            KalahGamePit oppositePit = game.getBoard().getOppositePit(endPit);
            if (oppositePit.getStoneCount() > 0) {
                KalahGamePit house = game.getBoard().getPlayerHome(endPit.getPlayerIndex());
                house.setStoneCount((house.getStoneCount() + oppositePit.getStoneCount()) + endPit.getStoneCount());
                oppositePit.setStoneCount(0);
                endPit.setStoneCount(0);
            }
        }
    }

    /**
     * Determine the player turn to play the game
     * @param game as an object
     * @param endPit as an object
     */
    private void nextPlayerTurnRule(KalahGame game, KalahGamePit endPit){
        if(endPit.isPlayer1Home() && game.getGameStatus().equals(KalahGameStatus.PLAYER1_TURN)){
            game.setGameStatus(KalahGameStatus.PLAYER1_TURN);
        }
        else if(endPit.isPlayer2Home() && game.getGameStatus().equals(KalahGameStatus.PLAYER2_TURN)){
            game.setGameStatus(KalahGameStatus.PLAYER2_TURN);
        }
        else{
            KalahGameStatus changeStage = game.getGameStatus() == KalahGameStatus.PLAYER1_TURN? KalahGameStatus.PLAYER2_TURN : KalahGameStatus.PLAYER1_TURN;
            game.setGameStatus(changeStage);
        }
    }
}
