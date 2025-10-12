package com.example.lms.groups.exception;

public class InvalidCourseException extends RuntimeException {
  public InvalidCourseException(String message) {
    super(message);
  }
}