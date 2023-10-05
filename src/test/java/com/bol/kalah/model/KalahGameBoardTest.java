package com.bol.kalah.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bol.kalah.exception.KalahRuntimeException;
import org.junit.jupiter.api.Test;

public class KalahGameBoardTest {

    @Test
    public void testCreateBoard(){
        KalahGameBoard board = createBoard();
        assertNotNull(board.getPits());
        assertEquals(14, board.getPits().size());
    }

    @Test
    public void testGetStoneCountByPitIndex(){
        KalahGameBoard board = createBoard();
        Integer pit1Stone = board.getStoneCountByPitIndex(1);
        Integer home1Stone = board.getStoneCountByPitIndex(7);
        Integer pit8Stone = board.getStoneCountByPitIndex(8);
        Integer home2Stone = board.getStoneCountByPitIndex(14);
        assertEquals(Integer.valueOf(6), pit1Stone);
        assertEquals(Integer.valueOf(0), home1Stone);
        assertEquals(Integer.valueOf(6), pit8Stone);
        assertEquals(Integer.valueOf(0), home2Stone);
    }

    @Test
    public void testGetPlayerHome(){
        KalahGameBoard board = createBoard();
        KalahGamePit home1 = board.getPlayerHome(KalahGamePlayer.PLAYER1_INDEX);
        KalahGamePit home2 = board.getPlayerHome(KalahGamePlayer.PLAYER2_INDEX);
        assertEquals(Integer.valueOf(7), home1.getPitIndex());
        assertEquals(Integer.valueOf(14), home2.getPitIndex());
    }

    @Test
    public void testErrorPlayerHome(){
        KalahRuntimeException exception = assertThrows(KalahRuntimeException.class, () -> {
            KalahGameBoard board = createBoard();
            KalahGamePit home1 = board.getPlayerHome(Integer.valueOf(3));
        });
        assertEquals("PlayerIndex is not correct", exception.getMessage());
    }

    @Test
    public void testGetPitByPitIndex(){
        KalahGameBoard board = createBoard();
        KalahGamePit pit = board.getPitByPitIndex(2);
        assertEquals(Integer.valueOf(2), pit.getPitIndex());
        assertEquals(Integer.valueOf(1), pit.getPlayerIndex());
    }

    @Test
    public void testGetNextPit() {
        KalahGameBoard board = createBoard();
        KalahGamePit pit1 = board.getPitByPitIndex(1);
        KalahGamePit pit2 = board.getNextPit(pit1);
        KalahGamePit pit14 = board.getPitByPitIndex(14);
        KalahGamePit pit1Again = board.getNextPit(pit14);
        assertEquals(Integer.valueOf(2), pit2.getPitIndex());
        assertEquals(pit1, pit1Again);
    }

    @Test
    public void testGetOppositePit() {
        KalahGameBoard board = createBoard();
        KalahGamePit pit1 = board.getPitByPitIndex(1);
        KalahGamePit oppositePit = board.getOppositePit(pit1);
        KalahGamePit pit1Again = board.getOppositePit(oppositePit);
        assertEquals(Integer.valueOf(13), oppositePit.getPitIndex());
        assertEquals(pit1, pit1Again);
    }

    @Test
    public void testGetPlayer1PitStoneCount(){
        KalahGameBoard board = createBoard();
        Integer player1PitStoneCount = board.getPlayer1PitStoneCount();
        assertEquals(Integer.valueOf(36), player1PitStoneCount);
    }

    @Test
    public void testGetPlayer2PitStoneCount(){
        KalahGameBoard board = createBoard();
        board.getPits().get(8).setStoneCount(0);
        Integer player2PitStoneCount = board.getPlayer2PitStoneCount();
        assertEquals(Integer.valueOf(30), player2PitStoneCount);
    }

    private static KalahGameBoard createBoard(){
        KalahGamePlayer player1 = new KalahGamePlayer(KalahGamePlayer.PLAYER1_INDEX, "player1");
        KalahGamePlayer player2 = new KalahGamePlayer(KalahGamePlayer.PLAYER2_INDEX, "player2");
        return new KalahGameBoard(KalahGameBoard.INITIAL_STONE_ON_PIT, player1, player2);
    }
}
