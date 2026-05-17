package com.claude.tennis.services;

public class PlayerAlreadyExistsException extends RuntimeException {
  public PlayerAlreadyExistsException(String lastname) {
    super("Player with lastname " + lastname + " already exists");
  }
}
