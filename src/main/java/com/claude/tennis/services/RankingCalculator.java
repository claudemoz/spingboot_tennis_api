package com.claude.tennis.services;

import java.util.ArrayList;
import java.util.List;
import com.claude.tennis.entities.PlayerEntity;

public class RankingCalculator {

  private final List<PlayerEntity> currentPlayersRanking;

  public RankingCalculator(List<PlayerEntity> currentPlayersRanking) {
    this.currentPlayersRanking = currentPlayersRanking;
  }

  public List<PlayerEntity> getNewPlayersRanking() {

    currentPlayersRanking.sort((player1, player2) -> Integer.compare(player2.getPoints(), player1.getPoints()));

    List<PlayerEntity> updatedPlayers = new ArrayList<>();

    for (int i = 0; i < currentPlayersRanking.size(); i++) {
      PlayerEntity updatedPlayer = currentPlayersRanking.get(i);
      updatedPlayer.setRank(i + 1);
      updatedPlayers.add(updatedPlayer);
    }

    return updatedPlayers;
  }
}