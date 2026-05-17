package com.claude.tennis.services;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.claude.tennis.dto.PlayerDto;
import com.claude.tennis.dto.PlayerToSave;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlayerServiceIntégrationTest {

  @Autowired
  PlayerService playerService;

  @Test
  public void shouldCreatePlayer() {
    // Given
    PlayerToSave playerToSave = new PlayerToSave("Nadal", "Rafael", LocalDate.of(1986, 6, 3), 10000);

    // When
    playerService.create(playerToSave);
    PlayerDto createdPlayer = playerService.getByLastName("Rafael");

    // Then
    // Assertions.assertThat(createdPlayer).extracting("lastName").isEqualTo("Rafael");
    Assertions.assertThat(createdPlayer.firstName()).isEqualTo("Nadal");
    Assertions.assertThat(createdPlayer.lastName()).isEqualTo("Rafael");
    Assertions.assertThat(createdPlayer.birthDate()).isEqualTo(LocalDate.of(1986, 6, 3));
    Assertions.assertThat(createdPlayer.rank().points()).isEqualTo(10000);
    Assertions.assertThat(createdPlayer.rank().position()).isEqualTo(1);
  }

  @Test
  public void shouldUpdatePlayer() {
    // Given
    PlayerToSave playerToSave = new PlayerToSave(
        "Rafael",
        "NadalTest",
        LocalDate.of(1986, Month.JUNE, 3),
        1000);

    // When
    playerService.update(playerToSave);
    PlayerDto updatedPlayer = playerService.getByLastName("NadalTest");

    // Then
    Assertions.assertThat(updatedPlayer.rank().position()).isEqualTo(3);
  }

  @Test
  public void shouldDeletePlayer() {
    // Given
    String playerToDelete = "DjokovicTest";

    // When
    playerService.delete(playerToDelete);
    List<PlayerDto> allPlayers = playerService.getAllPlayers();

    // Then
    Assertions.assertThat(allPlayers)
        .extracting("lastName", "rank.position")
        .containsExactly(Tuple.tuple("NadalTest", 1), Tuple.tuple("FedererTest", 2));
  }

  @Test
  public void shouldFailToDeletePlayer_WhenPlayerDoesNotExist() {
    // Given
    String playerToDelete = "DoeTest";

    // When / Then
    Assertions.assertThatThrownBy(() -> playerService.delete(playerToDelete))
        .isInstanceOf(PlayerNotFoundException.class)
        .hasMessage("Player with last name DoeTest could not be found.");
  }

}
