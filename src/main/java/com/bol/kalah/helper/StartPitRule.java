package com.bol.kalah.helper;


import com.bol.kalah.exception.KalahRuntimeException;
import com.bol.kalah.model.KalahGame;
import com.bol.kalah.model.KalahGameBoard;
import com.bol.kalah.model.KalahGamePit;
import com.bol.kalah.model.KalahGamePlayer;
import com.bol.kalah.model.KalahGameStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represents to check the start rule for distributing stones
 *
 * @author revathik
 */
@Slf4j
public class StartPitRule extends KalahGameRule {

    @Override
    public void apply(KalahGame game, KalahGamePit startPit) {
        log.debug("check rules for the start pit {}", startPit);
        checkPlayerTurnRule(game, startPit);
        checkEmptyStartRULE(startPit);
        this.next.apply(game, startPit);
    }

    /**
     * Determine the player turn
     * @param game as an object
     * @param startPit as an object
     */
    private void checkPlayerTurnRule(KalahGame game, KalahGamePit startPit){

        if(game.getGameStatus().equals(KalahGameStatus.INIT)) {
            KalahGameStatus gameStatus =  startPit.getPlayerIndex().equals(KalahGamePlayer.PLAYER1_INDEX) ? KalahGameStatus.PLAYER1_TURN : KalahGameStatus.PLAYER2_TURN;
            game.setGameStatus(gameStatus);
        }

        if((game.getGameStatus().equals(KalahGameStatus.PLAYER1_TURN) && startPit.getPitIndex() >= KalahGameBoard.PLAYER1_HOME) ||
                (game.getGameStatus().equals(KalahGameStatus.PLAYER2_TURN) && startPit.getPitIndex() <= KalahGameBoard.PLAYER1_HOME)){
            throw new KalahRuntimeException("Incorrect pit to play the kalah game");
        }
    }

    /**
     * Check empty for start rule
     * @param startPit as an object
     */
    private void checkEmptyStartRULE(KalahGamePit startPit){
        if(startPit.getStoneCount() == 0){
            throw new KalahRuntimeException("Can not start from empty pit");
        }
    }
}
