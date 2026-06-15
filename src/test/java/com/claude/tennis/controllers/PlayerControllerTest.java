package com.claude.tennis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.claude.tennis.data.Playerlist;
import com.claude.tennis.dto.PlayerDto;
import com.claude.tennis.dto.RankDto;
import com.claude.tennis.services.PlayerNotFoundException;
import com.claude.tennis.services.PlayerService;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.claude.tennis.security.ApplicationWithoutSecurity;

@WebMvcTest(controllers = PlayerController.class)
@ActiveProfiles("test")
@Import(ApplicationWithoutSecurity.class)
public class PlayerControllerTest {

  @Autowired
  private MockMvcTester mockMvc;

  @MockitoBean
  private PlayerService playerService;

  @Test
  public void shouldListAllPlayers() {
    // Given
    when(playerService.getAllPlayers()).thenReturn(Playerlist.ALL);

    // When
    var response = mockMvc.get().uri("/players")
        .accept(MediaType.APPLICATION_JSON)
        .exchange();

    // Then
    var json = response.assertThat().hasStatus(HttpStatus.OK).bodyJson();
    json.extractingPath("$.length()").isEqualTo(4);
    json.extractingPath("$[0].lastName").isEqualTo("Nadal");
    json.extractingPath("$[1].lastName").isEqualTo("Djokovic");
    json.extractingPath("$[2].lastName").isEqualTo("Federer");
    json.extractingPath("$[3].lastName").isEqualTo("Murray");
  }

  @Test
  public void shouldRetrievePlayer() {
    // Given
    String playerToRetrieve = "nadal";
    when(playerService.getByLastName(playerToRetrieve)).thenReturn(
        new PlayerDto("Rafael", "Nadal", LocalDate.of(1986, 6, 3), new RankDto(1, 5000)));
    // When
    var response = mockMvc.get().uri("/players/nadal")
        .accept(MediaType.APPLICATION_JSON)
        .exchange();

    // Then
    var json = response.assertThat().hasStatus(HttpStatus.OK).bodyJson();
    json.extractingPath("$.lastName").isEqualTo("Nadal");
    json.extractingPath("$.rank.position").isEqualTo(1);
  }

  @Test
  public void shouldReturn404NotFound_WhenPlayerDoesNotExist() {
    // Given
    String playerToRetrieve = "doe";
    when(playerService.getByLastName(playerToRetrieve)).thenThrow(new PlayerNotFoundException(playerToRetrieve));

    // When
    var response = mockMvc.get().uri("/players/doe")
        .accept(MediaType.APPLICATION_JSON)
        .exchange();

    // Then
    var json = response.assertThat().hasStatus(HttpStatus.NOT_FOUND).bodyJson();
    json.extractingPath("$.errorDetails").isEqualTo("Player with lastname doe could not be found");
  }
}