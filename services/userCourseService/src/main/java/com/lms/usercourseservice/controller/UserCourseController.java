package com.lms.usercourseservice.controller;

import com.lms.usercourseservice.model.UserCourse;
import com.lms.usercourseservice.service.UserCourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user-courses")
public class UserCourseController {
  private final UserCourseService service;

  public UserCourseController(UserCourseService service) {
    this.service = service;
  }

  @PostMapping("/enroll")
  public UserCourse enroll(@RequestParam UUID userId, @RequestParam String courseId) {
    return service.enrollUserToCourse(userId, courseId);
  }

  @GetMapping("/user/{userId}")
  public List<UserCourse> getCoursesOfUser(@PathVariable UUID userId) {
    return service.getCoursesOfUser(userId);
  }

  @GetMapping("/course/{courseId}")
  public List<UserCourse> getUsersOfCourse(@PathVariable String courseId) {
    return service.getUsersOfCourse(courseId);
  }

  @DeleteMapping("/unenroll")
  public void unenroll(@RequestParam UUID userId, @RequestParam String courseId) {
    service.unenrollUserFromCourse(userId, courseId);
  }
}