package com.claude.tennis.mappers;

import org.springframework.stereotype.Component;

import com.claude.tennis.dto.PlayerDto;
import com.claude.tennis.dto.PlayerToSave;
import com.claude.tennis.dto.RankDto;
import com.claude.tennis.entities.PlayerEntity;
import com.claude.tennis.entities.Rank;

@Component
public class PlayerMapper {

  public PlayerDto toDto(PlayerEntity entity) {
    return new PlayerDto(
        entity.getLastName(),
        entity.getFirstName(),
        entity.getBirthDate(),
        new RankDto(entity.getRank(), entity.getPoints()));
  }

  public PlayerEntity toEntity(PlayerToSave playerToSave) {
    PlayerEntity entity = new PlayerEntity(
        playerToSave.lastName(),
        playerToSave.firstName(),
        playerToSave.birthDate(),
        new Rank(999999999, playerToSave.points()));

    // entity.setFirstName(playerToSave.firstName());
    // entity.setLastName(playerToSave.lastName());
    // entity.setBirthDate(playerToSave.birthDate());
    // entity.setPoints(playerToSave.points());
    // entity.setRank(999999999);
    return entity;
  }

  public void updateEntity(PlayerEntity entity, PlayerToSave playerToSave) {
    entity.setFirstName(playerToSave.firstName());
    entity.setBirthDate(playerToSave.birthDate());
    entity.setPoints(playerToSave.points());

  }
}
