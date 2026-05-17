package com.claude.tennis.services;

public class PlayerNotFoundException extends RuntimeException {
  public PlayerNotFoundException(String lastname) {
    super("Player with lastname " + lastname + " could not be found");
  }
}
