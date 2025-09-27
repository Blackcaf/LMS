package com.lms.usercourseservice.service;

import com.lms.usercourseservice.model.UserCourse;
import com.lms.usercourseservice.repository.UserCourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserCourseService {
  private final UserCourseRepository repository;

  public UserCourseService(UserCourseRepository repository) {
    this.repository = repository;
  }

  public UserCourse enrollUserToCourse(UUID userId, String courseId) {
    UserCourse uc = new UserCourse();
    uc.setUserId(userId);
    uc.setCourseId(courseId);
    return repository.save(uc);
  }

  public List<UserCourse> getCoursesOfUser(UUID userId) {
    return repository.findByUserId(userId);
  }

  public List<UserCourse> getUsersOfCourse(String courseId) {
    return repository.findByCourseId(courseId);
  }

  public void unenrollUserFromCourse(UUID userId, String courseId) {
    repository.deleteByUserIdAndCourseId(userId, courseId);
  }
}