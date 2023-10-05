package com.bol.kalah.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represent the pit of the kalah game board
 *
 * @author revathik
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KalahGamePit {

    /**
     * Holds the index of the pit
     */
    @NotNull
    private Integer pitIndex;

    /**
     * Holds the stone count
     */
    @NotNull
    private Integer stoneCount;

    /**
     * Holds the index of the player
     */
    @NotNull
    private Integer playerIndex;

    /**
     * This method is use to determine different between pit & player home.
     * @return Boolean false if player1 with house2 Or player 2 with home1, otherwise true
     */
    public Boolean isDistributable(KalahGameStatus gameStatus){
        return (!gameStatus.equals(KalahGameStatus.PLAYER1_TURN) || !this.pitIndex.equals(KalahGameBoard.PLAYER2_HOME))
                && (!gameStatus.equals(KalahGameStatus.PLAYER2_TURN) || !this.pitIndex.equals(KalahGameBoard.PLAYER1_HOME));
    }


    /**
     * This method is use to determine the ownership of current player.
     * Player1 owns pit index from 1-7. Player 2 owns pit index from 8-14.
     *
     * @param gameStatus current game state. In this case player turn
     * @return True if current player is the owner of this pit otherwise false.
     */
    public Boolean isPlayerPit(KalahGameStatus gameStatus){

        if(gameStatus.equals(KalahGameStatus.PLAYER1_TURN) && this.playerIndex.equals(KalahGamePlayer.PLAYER1_INDEX)) {
            return true;
        }else if(gameStatus.equals(KalahGameStatus.PLAYER2_TURN) && this.playerIndex.equals(KalahGamePlayer.PLAYER2_INDEX)) {
            return true;
        }

        return false;
    }


    /**
     * This method determine that if this pit is use as Pit or as home.
     *  Pit index 7 & 14 is using as Home
     * @return True is pit uses as home otherwise false.
     */
    public Boolean isHome(){
        return this.pitIndex.equals(KalahGameBoard.PLAYER1_HOME) || this.pitIndex.equals(KalahGameBoard.PLAYER2_HOME);
    }



    /**
     * Find the next pit index
     * @return pitIndex of the next Pit
     */
    public Integer nextPitIndex(){
        Integer index = this.pitIndex + 1;
        if(index > KalahGameBoard.PLAYER2_HOME) {
            index = 1;
        }
        return index;
    }


    /**
     * Determine the pit as player1 house.
     * @return true if player1 house
     */
    public Boolean isPlayer1Home(){
        return this.playerIndex.equals(KalahGamePlayer.PLAYER1_INDEX) && this.pitIndex.equals(KalahGameBoard.PLAYER1_HOME);

    }

    /**
     * Determine the pit as player2 home.
     * @return true if player2 home
     */
    public Boolean isPlayer2Home(){
        return this.playerIndex.equals(KalahGamePlayer.PLAYER2_INDEX) && this.pitIndex.equals(KalahGameBoard.PLAYER2_HOME);
    }

    /**
     * This method return the opponent pit index.
     * @return pitIndex of the opponent pit.
     */
    public Integer getOpponentPitIndex(){
        return  (KalahGameBoard.PIT_START_INDEX + KalahGameBoard.PIT_END_INDEX - 1) - this.getPitIndex();
    }
}
