package com.claude.tennis.services;

import java.util.ArrayList;
import java.util.List;

import com.claude.tennis.Playerlist;
import com.claude.tennis.dto.PlayerDto;
import com.claude.tennis.dto.PlayerToSave;
import com.claude.tennis.dto.RankDto;
import com.claude.tennis.entities.PlayerEntity;
import com.claude.tennis.mappers.PlayerMapper;

public class RankingCalculator {

  private final List<PlayerEntity> currentPlayersRanking;
  private PlayerMapper playerMapper;

  public RankingCalculator(List<PlayerEntity> currentPlayersRanking) {
    this.currentPlayersRanking = currentPlayersRanking;
  }

  public List<PlayerEntity> getNewPlayersRanking() {

    currentPlayersRanking.sort((player1, player2) -> Integer.compare(player2.getPoints(), player1.getPoints()));

    List<PlayerEntity> updatedPlayers = new ArrayList<>();

    for (int i = 0; i < currentPlayersRanking.size(); i++) {
      PlayerEntity updatedPlayer = currentPlayersRanking.get(i);
      updatedPlayer.setRank(i + 1);
    }

    return updatedPlayers;
  }
}