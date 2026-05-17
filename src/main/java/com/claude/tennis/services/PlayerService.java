package com.claude.tennis.services;

import com.claude.tennis.mappers.PlayerMapper;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.claude.tennis.Playerlist;
import com.claude.tennis.dto.PlayerDto;
import com.claude.tennis.dto.PlayerToSave;
import com.claude.tennis.dto.RankDto;
import com.claude.tennis.entities.PlayerEntity;
import com.claude.tennis.repositories.PlayerRepository;

@Service
public class PlayerService {

  private final PlayerMapper playerMapper;
  private PlayerRepository playerRepository;

  PlayerService(PlayerMapper playerMapper, PlayerRepository playerRepository) {
    this.playerMapper = playerMapper;
    this.playerRepository = playerRepository;
  }

  public List<PlayerDto> getAllPlayers() {
    // return playerRepository.findAll().stream()
    // .map(playerEntity -> new PlayerDto(
    // playerEntity.getFirstName(),
    // playerEntity.getLastName(),
    // playerEntity.getBirthDate(),
    // new RankDto(
    // playerEntity.getRank(),
    // playerEntity.getPoints())))
    // .sorted(Comparator.comparing(player -> player.rank().position()))
    // .collect(Collectors.toList());

    return playerRepository.findAll().stream()
        .map(playerMapper::toDto)
        .sorted(Comparator.comparing(p -> p.rank().position()))
        .collect(Collectors.toList());
  }

  public PlayerDto getByLastName(@PathVariable("name") String lastName) {
    Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(lastName);
    if (player.isEmpty()) {
      throw new PlayerNotFoundException(lastName);
    }

    PlayerEntity playerEntity = player.get();
    // return new PlayerDto(
    // playerEntity.getFirstName(),
    // playerEntity.getLastName(),
    // playerEntity.getBirthDate(),
    // new RankDto(
    // playerEntity.getRank(),
    // playerEntity.getPoints()));

    return playerMapper.toDto(playerEntity);

  }

  public PlayerDto create(PlayerToSave playerToSave) {
    // PlayerEntity playerEntity = new PlayerEntity();
    // playerEntity.setFirstName(playerToSave.firstName());
    // playerEntity.setLastName(playerToSave.lastName());
    // playerEntity.setBirthDate(playerToSave.birthDate());
    // playerEntity.setPoints(playerToSave.points());

    Optional<PlayerEntity> existingPlayer = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
    if (existingPlayer.isPresent()) {
      throw new PlayerAlreadyExistsException(playerToSave.lastName());
    }
    PlayerEntity playerEntity = playerMapper.toEntity(playerToSave);
    playerRepository.save(playerEntity);

    RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
    List<PlayerEntity> updatedPlayers = rankingCalculator.getNewPlayersRanking();
    playerRepository.saveAll(updatedPlayers);

    return getByLastName(playerEntity.getLastName());
  }

  public PlayerDto update(PlayerToSave playerToSave) {
    Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
    if (player.isEmpty()) {
      throw new PlayerNotFoundException(playerToSave.lastName());
    }
    PlayerEntity playerEntity = player.get();
    // playerEntity.setFirstName(playerToSave.firstName());
    // playerEntity.setBirthDate(playerToSave.birthDate());
    // playerEntity.setPoints(playerToSave.points());

    playerMapper.updateEntity(playerEntity, playerToSave);
    playerRepository.save(playerEntity);

    RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
    List<PlayerEntity> updatedPlayers = rankingCalculator.getNewPlayersRanking();
    playerRepository.saveAll(updatedPlayers);

    return getByLastName(playerEntity.getLastName());
  }

  // private PlayerDto getPlayerNewRanking(List<PlayerEntity> existingPlayers) {
  // RankingCalculator rankingCalculator = new RankingCalculator(existingPlayers);
  // List<PlayerEntity> players = rankingCalculator.getNewPlayersRanking();

  // return players.stream()
  // .filter(player -> player.getLastName().equals(playerToSave.lastName()))
  // .findFirst().get();
  // }

  public void delete(String lastName) {
    Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(lastName);
    if (player.isEmpty()) {
      throw new PlayerNotFoundException(lastName);
    }

    playerRepository.delete(player.get());

    RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
    List<PlayerEntity> updatedPlayers = rankingCalculator.getNewPlayersRanking();
    playerRepository.saveAll(updatedPlayers);

  }
}
