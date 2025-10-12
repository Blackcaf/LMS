package com.example.lms.groups.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(GroupNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleGroupNotFound(GroupNotFoundException ex) {
    log.error("Group not found: {}", ex.getMessage());
    return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidUserException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidUser(InvalidUserException ex) {
    log.error("Invalid user: {}", ex.getMessage());
    return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidCourseException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidCourse(InvalidCourseException ex) {
    log.error("Invalid course: {}", ex.getMessage());
    return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
    log.error("Unexpected error: {}", ex.getMessage(), ex);
    return buildErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("timestamp", LocalDateTime.now());
    errorResponse.put("status", status.value());
    errorResponse.put("error", status.getReasonPhrase());
    errorResponse.put("message", message);
    return ResponseEntity.status(status).body(errorResponse);
  }
}