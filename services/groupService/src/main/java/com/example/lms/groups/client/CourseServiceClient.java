package com.example.lms.groups.client;

import com.example.lms.groups.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CourseServiceClient {

  private final RestTemplate restTemplate;

  @Value("${services.course.url:http://localhost:8082}")
  private String courseServiceUrl;

  public CourseDto getCourseById(UUID courseId) {
    try {
      String url = courseServiceUrl + "/api/courses/" + courseId;
      log.debug("Fetching course from: {}", url);
      return restTemplate.getForObject(url, CourseDto.class);
    } catch (Exception e) {
      log.error("Error fetching course with id: {}", courseId, e);
      throw new RuntimeException("Course service unavailable", e);
    }
  }

  public boolean validateCourse(UUID courseId) {
    try {
      CourseDto course = getCourseById(courseId);
      return course != null;
    } catch (Exception e) {
      log.error("Error validating course with id: {}", courseId, e);
      return false;
    }
  }
}