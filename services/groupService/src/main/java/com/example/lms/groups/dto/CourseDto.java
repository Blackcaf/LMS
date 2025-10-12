package com.example.lms.groups.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {
  private UUID id;
  private String title;
  private String description;
  private String instructor;
  private Integer duration;
  private LocalDateTime createdAt;
}