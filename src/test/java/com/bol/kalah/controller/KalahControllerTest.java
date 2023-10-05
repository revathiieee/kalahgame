package com.bol.kalah.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bol.kalah.exception.KalahRuntimeException;
import com.bol.kalah.model.KalahGame;
import com.bol.kalah.service.KalahGameService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
public class KalahControllerTest {

  private static final Integer INITIAL_STONE_ON_PIT = 6;
  private static final Integer INITIAL_STONE_ON_HOUSE = 0;
  private static final Integer PLAYER1_INDEX = 1;
  private static final Integer PLAYER2_INDEX = 2;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Mock
  private KalahGameService kalahGameService;

  @InjectMocks
  private KalahGameController kalahGameController;


  private final ObjectMapper objectMapper = new ObjectMapper();

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }


  @Test
  @DirtiesContext
  public void testCreateGame() throws Exception {

    MockHttpServletRequestBuilder initGameRequest = MockMvcRequestBuilders.post("/games");
    MvcResult result = mockMvc.perform(initGameRequest)
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())

        //check game state
        .andExpect(MockMvcResultMatchers.jsonPath("$.gameStatus").value("INIT"))

        //check total pit size
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(14)))

        //check pit index
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.1.pitIndex").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.8.pitIndex").value(8))
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.pitIndex").value(14))

        //check player index
        .andExpect(MockMvcResultMatchers.jsonPath("$.player1.playerIndex").value(KalahControllerTest.PLAYER1_INDEX))
        .andExpect(MockMvcResultMatchers.jsonPath("$.player2.playerIndex").value(KalahControllerTest.PLAYER2_INDEX))

        //check pit owner
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.6.playerIndex").value(KalahControllerTest.PLAYER1_INDEX))
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.13.playerIndex").value(KalahControllerTest.PLAYER2_INDEX))
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.playerIndex").value(KalahControllerTest.PLAYER2_INDEX))

        //check  initial pit stone count
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.5.stoneCount").value(KalahControllerTest.INITIAL_STONE_ON_PIT))
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.12.stoneCount").value(KalahControllerTest.INITIAL_STONE_ON_PIT))
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.stoneCount").value(KalahControllerTest.INITIAL_STONE_ON_HOUSE))
        .andReturn();
    int status = result.getResponse().getStatus();
    assertEquals(201, status);
  }

  @Test
  public void testCreateGameError() throws Exception{
    KalahRuntimeException exception = Assertions.assertThrows(KalahRuntimeException.class, () -> {
      when(kalahGameService.createGame(Mockito.any())).thenThrow(new KalahRuntimeException("Oh, Error"));
      kalahGameController.createBoard(Integer.valueOf(5));
    });
    assertEquals("Oh, Error", exception.getMessage());
  }

  @Test
  public void testPlayPitIndexError() {

    KalahRuntimeException exception = Assertions.assertThrows(KalahRuntimeException.class, () -> {
      kalahGameController.play("", Integer.valueOf(0));
    });
    assertEquals("Incorrect pit index", exception.getMessage());
  }

  @Test
  public void testPlayPlayerIndexError() {
    KalahRuntimeException exception = Assertions.assertThrows(KalahRuntimeException.class, () -> {
      kalahGameController.play("", Integer.valueOf(7));
    });
    assertEquals("Home stone is not allow to distribute", exception.getMessage());
  }

  @Test
  @DirtiesContext
  public void testPlayGame() throws Exception {

    MockHttpServletRequestBuilder initGameRequest = MockMvcRequestBuilders.post("/games");
    String responseString = mockMvc.perform(initGameRequest).andReturn().getResponse().getContentAsString();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    KalahGame game = objectMapper.readValue(responseString, KalahGame.class);

    MockHttpServletRequestBuilder playGame = MockMvcRequestBuilders.put("/games/"+game.getId()+"/pits/"+ 1);
    MvcResult result = mockMvc.perform(playGame)
        .andExpect(MockMvcResultMatchers.status().isOk())

        //check game id
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))

        //check total pit size
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(14)))

        //check player index
        .andExpect(MockMvcResultMatchers.jsonPath("$.player1.playerIndex").value(KalahControllerTest.PLAYER1_INDEX))
        .andExpect(MockMvcResultMatchers.jsonPath("$.player2.playerIndex").value(KalahControllerTest.PLAYER2_INDEX))

        //starting pit should be zero
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.1.stoneCount").value(0))

        //pit should increment by 1
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.2.stoneCount").value(7))
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.3.stoneCount").value(7))
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.4.stoneCount").value(7))
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.5.stoneCount").value(7))
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.6.stoneCount").value(7))

        //player 1 house should increment by 1
        .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.7.stoneCount").value(1))

        //check game state as end with player 1 house
        .andExpect(MockMvcResultMatchers.jsonPath("$.gameStatus").value("PLAYER1_TURN"))
        .andReturn();
    int status = result.getResponse().getStatus();
    assertEquals(200, status);
  }

}
