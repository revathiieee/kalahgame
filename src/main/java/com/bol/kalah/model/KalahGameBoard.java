package com.bol.kalah.model;


import com.bol.kalah.exception.KalahRuntimeException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the board of the Kalah Game which contain all pits
 *
 * @author revathik
 */
@Data
@NoArgsConstructor
public class KalahGameBoard {

    /**
     * Hold Pit Start Index as 1
     */
    public static final Integer PIT_START_INDEX = 1;
    /**
     * Hold Pit End Index as 14
     */
    public static final Integer PIT_END_INDEX = 14;
    /**
     * Hold Player1 Home as 7
     */
    public static final Integer PLAYER1_HOME = 7;
    /**
     * Hold Player2 Home as 14
     */
    public static final Integer PLAYER2_HOME = 14;
    /**
     * Hold initial stone pit as 6
     */
    public static final Integer INITIAL_STONE_ON_PIT = 6;
    /**
     * Hold initial stone home as 0
     */
    public static final Integer INITIAL_STONE_ON_HOME = 0;

    /**
     * Holds the pits info as map where kalahgamepit object as a value
     */
    private Map<Integer, KalahGamePit> pits;

    /**
     * Constructor holds initialStoneOnPit, player1 and player2
     * @param initialStoneOnPit initial stone on pit
     * @param player1 Player 1
     * @param player2 Player 2
     */
    public KalahGameBoard(Integer initialStoneOnPit, KalahGamePlayer player1, KalahGamePlayer player2) {
        this.pits = initPit(initialStoneOnPit, player1, player2);
    }

    /**
     * Getting stone count by pit index
     * @param pitIndex Index of the pit
     * @return total stone on a pit
     */
    public Integer getStoneCountByPitIndex(Integer pitIndex){
        return getPitByPitIndex(pitIndex).getStoneCount();
    }

    /**
     * Getting the player home to play the game
     * @param playerIndex
     * @return
     */
    public KalahGamePit getPlayerHome(Integer playerIndex){
        if(playerIndex.equals(KalahGamePlayer.PLAYER1_INDEX)) {
            return pits.get(KalahGameBoard.PLAYER1_HOME);
        }
        else if(playerIndex.equals(KalahGamePlayer.PLAYER2_INDEX)){
            return pits.get(KalahGameBoard.PLAYER2_HOME);
        }
        throw new KalahRuntimeException("PlayerIndex is not correct");
    }

    /**
     * Get Pit by Pit Index
     * @param pitIndex pit index
     * @return the kalahgamepit object
     */
    public KalahGamePit getPitByPitIndex(Integer pitIndex){
        return pits.get(pitIndex);
    }

    /**
     * Get the next pit from KalahGamePit object
     * @param pit kalahgame pit object
     * @return the KalahGamePit object
     */
    public KalahGamePit getNextPit(KalahGamePit pit){
        return pits.get(pit.nextPitIndex());
    }

    /**
     * Get the Opposite pit from KalahGamePit object
     * @param pit kalahgame pit object
     * @return the KalahGamePit object
     */
    public KalahGamePit getOppositePit(KalahGamePit pit){
        return pits.get(pit.getOpponentPitIndex());
    }

    /**
     * Get the player1 pit stone count
     * @return the count
     */
    public Integer getPlayer1PitStoneCount(){
        Integer player1PitStoneCount = 0;
        for(int i = KalahGameBoard.PIT_START_INDEX; i < KalahGameBoard.PLAYER1_HOME; i++){
            player1PitStoneCount += this.getPits().get(i).getStoneCount();
        }
        return player1PitStoneCount;
    }

    /**
     * Get the player2 pit stone count
     * @return the count
     */
    public Integer getPlayer2PitStoneCount(){
        Integer player2PitStoneCount = 0;
        for(int i=KalahGameBoard.PLAYER1_HOME + 1; i < KalahGameBoard.PLAYER2_HOME; i++){
            player2PitStoneCount += this.getPits().get(i).getStoneCount();
        }
        return player2PitStoneCount;
    }

    /**
     * Determine the pit for player1 and player2
     * @param initialStoneOnPit initial stone pit
     * @param player1 player1 object
     * @param player2 player2 object
     * @return the map
     */
    private Map<Integer, KalahGamePit> initPit(Integer initialStoneOnPit, KalahGamePlayer player1, KalahGamePlayer player2){

        Map<Integer, KalahGamePit> pits = new ConcurrentHashMap<>();
        for(int i= KalahGameBoard.PIT_START_INDEX; i < KalahGameBoard.PLAYER1_HOME; i++){
            KalahGamePit pit = new KalahGamePit(i, initialStoneOnPit, player1.getPlayerIndex());
            pits.put(i, pit);
        }
        KalahGamePit home1 = new KalahGamePit(KalahGameBoard.PLAYER1_HOME, KalahGameBoard.INITIAL_STONE_ON_HOME, player1.getPlayerIndex());
        pits.put(KalahGameBoard.PLAYER1_HOME, home1);


        for(int i= KalahGameBoard.PLAYER1_HOME + 1; i < KalahGameBoard.PLAYER2_HOME; i++){
            KalahGamePit pit = new KalahGamePit(i, initialStoneOnPit, player2.getPlayerIndex());
            pits.put(i, pit);
        }
        KalahGamePit home2 = new KalahGamePit(KalahGameBoard.PLAYER2_HOME, KalahGameBoard.INITIAL_STONE_ON_HOME, player2.getPlayerIndex());
        pits.put(KalahGameBoard.PLAYER2_HOME, home2);

        return pits;
    }
}
