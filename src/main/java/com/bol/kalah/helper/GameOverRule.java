package com.bol.kalah.helper;


import com.bol.kalah.model.KalahGame;
import com.bol.kalah.model.KalahGameBoard;
import com.bol.kalah.model.KalahGamePit;
import com.bol.kalah.model.KalahGameStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represents the game over rule logic
 *
 * @author revathik
 */
@Slf4j
public class GameOverRule extends KalahGameRule {
    @Override
    public void apply(KalahGame game, KalahGamePit currentPit) {
        log.debug("checking game end rule");
        Integer player1StoneCount = game.getBoard().getPlayer1PitStoneCount();
        Integer player2StoneCount = game.getBoard().getPlayer2PitStoneCount();

        if( player1StoneCount == 0 || player2StoneCount == 0){
            game.setGameStatus(KalahGameStatus.GAMEOVER);
            //Player1 stone count
            KalahGamePit home1 = game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME);
            home1.setStoneCount(home1.getStoneCount() + player1StoneCount);
            //Player2 stone count
            KalahGamePit home2 = game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME);
            home2.setStoneCount(home2.getStoneCount() + player2StoneCount);
            //Determining the kalah game result
            determineResult(game, home1.getStoneCount(), home2.getStoneCount());
            //Reset the kalah game
            resetBoard(game);
        }
    }

    /**
     * Method to reset the kalah game
     * @param game as object
     */
    private void resetBoard(KalahGame game){
      game.getBoard().getPits().keySet().stream()
          .filter(key -> !key.equals(KalahGameBoard.PLAYER1_HOME) && !key.equals(KalahGameBoard.PLAYER2_HOME))
          .map(key -> game.getBoard().getPits().get(key))
          .forEachOrdered(pit -> pit.setStoneCount(0));
    }

    /**
     * Method to determine the result
     * @param game as Game object
     * @param home1StoneCount as stone count of home1
     * @param home2StoneCount as stone count of home2
     */
    private void determineResult(KalahGame game, Integer home1StoneCount, Integer home2StoneCount){
        if(home1StoneCount > home2StoneCount){
            game.setWinner(game.getPlayer1());
        }else if(home1StoneCount < home2StoneCount){
            game.setWinner(game.getPlayer2());
        }else{
            game.setWinner(null);
        }
    }
}