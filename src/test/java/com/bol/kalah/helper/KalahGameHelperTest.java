package com.bol.kalah.helper;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bol.kalah.exception.KalahRuntimeException;
import com.bol.kalah.model.KalahGame;
import com.bol.kalah.model.KalahGameBoard;
import com.bol.kalah.model.KalahGamePit;
import com.bol.kalah.model.KalahGameStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class KalahGameHelperTest {

    @InjectMocks
    private KalahGameHelper gameHelper;

    @Test
    public void testStartWithOwnPit(){
        KalahGame game = new KalahGame(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(1));
        assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(1));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(2) );
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(3));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(4));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(5));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(6));
        assertEquals(Integer.valueOf(1), game.getBoard().getStoneCountByPitIndex(7));
        assertEquals(KalahGameStatus.PLAYER1_TURN, game.getGameStatus());
        assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME).getStoneCount());
        assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME).getStoneCount());
    }

    @Test
    public void testNotStartWithEmptyPit(){
        KalahRuntimeException exception = Assertions.assertThrows(KalahRuntimeException.class, () -> {
            KalahGame game = new KalahGame(6);
            KalahGamePit pit = game.getBoard().getPits().get(2);
            pit.setStoneCount(0);
            gameHelper.play(game, game.getBoard().getPitByPitIndex(2));
        });
        assertEquals("Can not start from empty pit", exception.getMessage());
    }

    @Test
    public void testNotStartWithOpponentPit(){
        KalahRuntimeException exception = Assertions.assertThrows(KalahRuntimeException.class, () -> {
            KalahGame game = new KalahGame(6);
            game.setGameStatus(KalahGameStatus.PLAYER2_TURN);
            gameHelper.play(game, game.getBoard().getPitByPitIndex(2));
        });
        assertEquals("Incorrect pit to play the kalah game", exception.getMessage());
    }

    @Test
    public void testDistributeStoneFromPlayer2PitToPlayer1Pit() {
        KalahGame game = new KalahGame(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(12));
        assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(12));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(13));
        assertEquals(Integer.valueOf(1), game.getBoard().getStoneCountByPitIndex(14));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(1));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(2));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(3));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(4));
        assertEquals(KalahGameStatus.PLAYER1_TURN, game.getGameStatus());
        assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME).getStoneCount());
        assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME).getStoneCount());
    }

    @Test
    public void testDistributeStoneFromPlayer1PitToPlayer2Pit(){
        KalahGame game = new KalahGame(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(4));
        assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(4));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(5));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(6));
        assertEquals(Integer.valueOf(1), game.getBoard().getStoneCountByPitIndex(7));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(8));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(9));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(10));
        assertEquals(KalahGameStatus.PLAYER2_TURN, game.getGameStatus());
        assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME).getStoneCount());
        assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME).getStoneCount());
    }

    @Test
    public void testDistribute13Stone(){
        KalahGame game = new KalahGame(6);
        game.getBoard().getPits().get(4).setStoneCount(13);
        game.getBoard().getPits().get(10).setStoneCount(10);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(4));
        assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(4));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(5));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(6));
        assertEquals(Integer.valueOf(13), game.getBoard().getStoneCountByPitIndex(7));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(8));
        assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(9));
        assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(10));
        assertEquals(KalahGameStatus.PLAYER2_TURN, game.getGameStatus());
        assertEquals(Integer.valueOf(13), game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME).getStoneCount());
        assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME).getStoneCount());
    }

    @Test
    public void testIncreaseHomeStoneOnOwnEmptyPit() {
        KalahGame game = new KalahGame(6);
        KalahGamePit pit1 = game.getBoard().getPitByPitIndex(1);
        pit1.setStoneCount(2);

        KalahGamePit pit2 = game.getBoard().getPitByPitIndex(3);
        pit2.setStoneCount(0);

        gameHelper.play(game, game.getBoard().getPitByPitIndex(1));

        assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(1));
        assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(3) );
        assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(11));
        assertEquals(KalahGameStatus.PLAYER2_TURN, game.getGameStatus());
        assertEquals(Integer.valueOf(7), game.getBoard().getPits().get(KalahGameBoard.PLAYER1_HOME).getStoneCount());
        assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(KalahGameBoard.PLAYER2_HOME).getStoneCount());
    }

    @Test
    public void testChangeGameToPlayerTurn1() {
        KalahGame game = new KalahGame(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(1));
        assertEquals(KalahGameStatus.PLAYER1_TURN, game.getGameStatus());
    }

    @Test
    public void testChangeGameToPlayerTurn2() {
        KalahGame game = new KalahGame(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(2));
        assertEquals(KalahGameStatus.PLAYER2_TURN, game.getGameStatus());
    }


    @Test
    public void testChangeGameToPlayerTurn2Again() {
        KalahGame game = new KalahGame(6);
        KalahGamePit pit = game.getBoard().getPits().get(8);
        pit.setStoneCount(6);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(8));
        assertEquals(KalahGameStatus.PLAYER2_TURN, game.getGameStatus());
    }


    @Test
    public void testGameOverRule() {
        KalahGame game = new KalahGame(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            KalahGamePit pit = game.getBoard().getPits().get(key);
            if(!pit.isHome()) {
                pit.setStoneCount(0);
            }
        }
        game.getBoard().getPits().get(6).setStoneCount(1);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(6));
        assertEquals(KalahGameStatus.GAMEOVER, game.getGameStatus());
        assertEquals(game.getWinner(), game.getPlayer1());
    }

    @Test
    public void testPlayer1Win() {
        KalahGame game = new KalahGame(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            KalahGamePit pit = game.getBoard().getPits().get(key);
            if(!pit.isHome()) {
                pit.setStoneCount(0);
            }
        }
        KalahGamePit lastPit = game.getBoard().getPits().get(6);
        lastPit.setStoneCount(1);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(6));
        assertEquals(KalahGameStatus.GAMEOVER, game.getGameStatus());
        assertEquals(game.getWinner(), game.getPlayer1());
    }

    @Test
    public void testPlayer2Win(){
        KalahGame game = new KalahGame(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            KalahGamePit pit = game.getBoard().getPits().get(key);
            if(!pit.isHome()) {
                pit.setStoneCount(0);
            }
        }
        game.getBoard().getPits().get(13).setStoneCount(1);
        gameHelper.play(game, game.getBoard().getPitByPitIndex(13));
        assertEquals(KalahGameStatus.GAMEOVER, game.getGameStatus());
        assertEquals(game.getWinner(), game.getPlayer2());
    }

    @Test
    public void testDraw(){
        KalahGame game = new KalahGame(6);
        for(Integer key : game.getBoard().getPits().keySet()){
            KalahGamePit pit = game.getBoard().getPits().get(key);
            if(!pit.isHome()) {
                pit.setStoneCount(0);
            }
        }
        game.getBoard().getPits().get(6).setStoneCount(1);
        game.getBoard().getPits().get(14).setStoneCount(1);

        gameHelper.play(game, game.getBoard().getPitByPitIndex(6));

        assertEquals(KalahGameStatus.GAMEOVER, game.getGameStatus());
        assertEquals(game.getWinner(), null);
    }
}
