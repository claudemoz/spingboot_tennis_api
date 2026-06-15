package com.claude.tennis.services;

public class PlayerRetrieveServiceException extends RuntimeException {
  public PlayerRetrieveServiceException(Exception e) {
    super("Could not retrieve player data", e);
  }

}
