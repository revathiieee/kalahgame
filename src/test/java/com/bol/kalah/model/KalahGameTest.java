package com.bol.kalah.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class KalahGameTest {

    @Test
    public void shouldCreateGame(){
        KalahGame game = new KalahGame(KalahGameBoard.INITIAL_STONE_ON_PIT);
        assertEquals(KalahGamePlayer.PLAYER1_INDEX, game.getPlayer1().getPlayerIndex());
        assertEquals(KalahGamePlayer.PLAYER2_INDEX, game.getPlayer2().getPlayerIndex());
        assertNotNull(game.getBoard());
        assertNull(game.getWinner());
        assertNull(game.getId());
        assertEquals(14, game.getBoard().getPits().size());
        assertEquals(KalahGameStatus.INIT, game.getGameStatus());
    }
}
