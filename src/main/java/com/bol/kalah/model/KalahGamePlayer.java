package com.bol.kalah.model;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the player of the kalah game
 *
 * @author revathik
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KalahGamePlayer {

    /**
     * Hold player1 index as 1
     */
    public static final Integer PLAYER1_INDEX = 1;
    /**
     * Hold player2 index as 2
     */
    public static final Integer PLAYER2_INDEX = 2;

    /**
     * Holds the index value of the player
     */
    @NotNull
    private Integer playerIndex;

    /**
     * Holds the name of the player
     */
    @NotNull
    private String playerName;
}
