package com.example.lms.groups.repository;

import com.example.lms.groups.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
  List<Group> findByCourseId(UUID courseId);
}