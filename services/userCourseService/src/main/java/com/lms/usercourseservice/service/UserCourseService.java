package com.lms.usercourseservice.service;

import com.lms.usercourseservice.model.UserCourse;
import com.lms.usercourseservice.repository.UserCourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserCourseService {
  private final UserCourseRepository repository;

  public UserCourseService(UserCourseRepository repository) {
    this.repository = repository;
  }

  public UserCourse enrollUserToCourse(UUID userId, String courseId) {
    // Проверяем, не записан ли уже пользователь на курс
    List<UserCourse> existing = repository.findByUserIdAndCourseId(userId, courseId);
    if (!existing.isEmpty()) {
      throw new IllegalStateException("User is already enrolled in this course");
    }

    UserCourse uc = new UserCourse();
    uc.setUserId(userId);
    uc.setCourseId(courseId);
    return repository.save(uc);
  }

  @Transactional(readOnly = true)
  public List<UserCourse> getCoursesOfUser(UUID userId) {
    return repository.findByUserId(userId);
  }

  @Transactional(readOnly = true)
  public List<UserCourse> getUsersOfCourse(String courseId) {
    return repository.findByCourseId(courseId);
  }

  public void unenrollUserFromCourse(UUID userId, String courseId) {
    repository.deleteByUserIdAndCourseId(userId, courseId);
  }
}