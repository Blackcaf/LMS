package com.lms.usercourseservice.repository;

import com.lms.usercourseservice.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
  List<UserCourse> findByUserId(UUID userId);
  List<UserCourse> findByCourseId(String courseId);
  List<UserCourse> findByUserIdAndCourseId(UUID userId, String courseId);
  void deleteByUserIdAndCourseId(UUID userId, String courseId);
}