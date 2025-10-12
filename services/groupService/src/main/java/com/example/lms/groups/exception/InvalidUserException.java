package com.example.lms.groups.exception;

public class InvalidUserException extends RuntimeException {
  public InvalidUserException(String message) {
    super(message);
  }
}