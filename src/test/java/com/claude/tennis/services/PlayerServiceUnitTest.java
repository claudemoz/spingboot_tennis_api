package com.claude.tennis.services;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.claude.tennis.data.PlayerlistEntity;
import com.claude.tennis.dto.PlayerDto;
import com.claude.tennis.mappers.PlayerMapper;
import com.claude.tennis.repositories.PlayerRepository;

public class PlayerServiceUnitTest {

  @Mock
  PlayerRepository playerRepository;
  PlayerMapper playerMapper = new PlayerMapper();
  PlayerService playerService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    playerService = new PlayerService(playerMapper, playerRepository);
  }

  @Test
  public void shouldReturnPlayersRanking() {
    // Given
    Mockito.when(playerRepository.findAll()).thenReturn(PlayerlistEntity.ALL);

    // When
    List<PlayerDto> allPlayers = playerService.getAllPlayers();

    // Then
    Assertions.assertThat(allPlayers).extracting("lastName")
        .containsExactly("Nadal", "Djokovic", "Federer", "Murray");
  }

  @Test
  public void shouldReturnPlayerByLastName() {
    // Given
    Mockito.when(playerRepository.findOneByLastNameIgnoreCase("Nadal"))
        .thenReturn(Optional.of(PlayerlistEntity.RAFAEL_NADAL));

    // When
    PlayerDto player = playerService.getByLastName("Nadal");

    // Then
    Assertions.assertThat(player).extracting("firstName").isEqualTo("Rafael");
  }

  @Test
  public void shouldThrowExceptionWhenPlayerNotFound() {
    // Given
    String unknownLastNamePlayer = "Moz";
    Mockito.when(playerRepository.findOneByLastNameIgnoreCase(unknownLastNamePlayer))
        .thenReturn(Optional.empty());

    // When
    Throwable thrown = Assertions.catchThrowable(() -> playerService.getByLastName(unknownLastNamePlayer));

    // Then
    Assertions.assertThat(thrown).isInstanceOf(PlayerNotFoundException.class)
        .hasMessage("Player with lastname " + unknownLastNamePlayer + " could not be found");
  }

}
