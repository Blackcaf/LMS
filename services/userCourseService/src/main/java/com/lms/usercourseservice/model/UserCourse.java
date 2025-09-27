package com.lms.usercourseservice.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_courses")
public class UserCourse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "course_id", nullable = false)
  private String courseId;

  public Long getId() { return id; }
  public UUID getUserId() { return userId; }
  public void setUserId(UUID userId) { this.userId = userId; }
  public String getCourseId() { return courseId; }
  public void setCourseId(String courseId) { this.courseId = courseId; }
}