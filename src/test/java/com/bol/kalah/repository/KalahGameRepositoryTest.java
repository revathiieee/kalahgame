package com.bol.kalah.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bol.kalah.exception.KalahRuntimeException;
import com.bol.kalah.model.KalahGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class KalahGameRepositoryTest {

    @InjectMocks
    private KalahGameRepository gameRepository;

    @Test
    public void testCreateGame(){
        KalahGame game1 = gameRepository.createKalahGame(6);
        assertEquals(game1, game1);
    }

    @Test
    public void testFindByKalahGameId(){
        KalahGame game1 = gameRepository.createKalahGame(6);
        KalahGame game = gameRepository.findByKalahGameId(game1.getId());
        assertNotNull(game);
        assertEquals(game1, game1);
    }

    @Test
    public void testFindByKalahGameIdException(){
        KalahRuntimeException exception = Assertions.assertThrows(KalahRuntimeException.class, () -> {
            KalahGame game1 = gameRepository.createKalahGame(6);
            game1.setId("7");
            KalahGame game = gameRepository.findByKalahGameId(game1.getId());
        });
        assertEquals("Kalah Game is not found for this id: 7", exception.getMessage());
    }
}
