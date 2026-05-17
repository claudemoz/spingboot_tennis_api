package com.claude.tennis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claude.tennis.entities.PlayerEntity;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
  Optional<PlayerEntity> findOneByLastNameIgnoreCase(String lastName);
}
