package com.bol.kalah.controller;


import com.bol.kalah.exception.KalahRuntimeException;
import com.bol.kalah.model.KalahGame;
import com.bol.kalah.model.KalahGameBoard;
import com.bol.kalah.service.KalahGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Rest endpoint of this kalah game
 *
 * @author revathik
 */
@RestController
@RequestMapping("/games")
@Slf4j
public class KalahGameController {


    /**
     * Instantiates kalahgameservice
     */
    private final KalahGameService kalahGameService;

    public KalahGameController(KalahGameService kalahGameService) {
        this.kalahGameService = kalahGameService;
    }


    /**
     * Endpoint to create a new Game
     * @param numberOfStone no of stones default is 6
     * @return Response body of KalahGame
     */
    @PostMapping
    @Operation(summary = "Submit a new  kalah game.", description = "Add New Kalah Game")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Successfully created a new game"),
            @ApiResponse(responseCode = "400", description = "An error has occurred when attempting to parse request"),
            @ApiResponse(responseCode = "500", description = "A technical error has occurred when attempting to create the game") })
    public ResponseEntity<KalahGame> createBoard(@RequestParam(name = "stone", defaultValue = "6", required = false) Integer numberOfStone){
        log.debug("initializing kalah game");
        return ResponseEntity.status(HttpStatus.CREATED).body(kalahGameService.createGame(numberOfStone));
    }

    /**
     * Endpoint to play the kalah game
     * @param gameId as Game Id
     * @param pitIndex as index of pit
     * @return KalahGame Object
     */
    @PutMapping("/{gameId}/pits/{pitIndex}")
    @Operation(summary = "Make a move to play the game", description = "Make a move to play the game")
    public ResponseEntity<KalahGame> play(@PathVariable String gameId, @PathVariable Integer pitIndex){
        log.debug("From game {}, player {} is moving stone from pit {}",gameId, pitIndex);
        if (pitIndex > KalahGameBoard.PIT_END_INDEX || pitIndex < KalahGameBoard.PIT_START_INDEX) {
            throw new KalahRuntimeException("Incorrect pit index");
        }
        if (pitIndex.equals(KalahGameBoard.PLAYER1_HOME) || pitIndex.equals(KalahGameBoard.PLAYER2_HOME)) {
            throw new KalahRuntimeException("Home stone is not allow to distribute");
        }
        return ResponseEntity.status(HttpStatus.OK).body(kalahGameService.play(gameId, pitIndex));
    }
}
