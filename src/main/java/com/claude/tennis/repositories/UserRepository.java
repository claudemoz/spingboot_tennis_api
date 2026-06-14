package com.claude.tennis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.claude.tennis.entities.UserEntity;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findOneWithRolesByLoginIgnoreCase(String login);
}