package com.example.lms.groups.service;

import com.example.lms.groups.client.CourseServiceClient;
import com.example.lms.groups.client.UserServiceClient;
import com.example.lms.groups.entity.Group;
import com.example.lms.groups.exception.GroupNotFoundException;
import com.example.lms.groups.exception.InvalidCourseException;
import com.example.lms.groups.exception.InvalidUserException;
import com.example.lms.groups.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

  private final GroupRepository repository;
  private final UserServiceClient userServiceClient;
  private final CourseServiceClient courseServiceClient;

  @Transactional
  public Group createGroup(Group group) {
    log.info("Creating group for course: {}", group.getCourseId());

    // Валидация курса
    if (!courseServiceClient.validateCourse(group.getCourseId())) {
      throw new InvalidCourseException("Course with id " + group.getCourseId() + " not found");
    }

    Group saved = repository.save(group);
    log.info("Group created with id: {}", saved.getId());
    return saved;
  }

  @Transactional
  public Group createGroup(UUID courseId, LocalDate startDate) {
    log.info("Creating group for course: {} with start date: {}", courseId, startDate);

    // Валидация курса
    if (!courseServiceClient.validateCourse(courseId)) {
      throw new InvalidCourseException("Course with id " + courseId + " not found");
    }

    Group group = Group.builder()
        .courseId(courseId)
        .startDate(startDate)
        .build();

    return repository.save(group);
  }

  @Transactional
  public Group addUserToGroup(UUID groupId, UUID userId) {
    log.info("Adding user {} to group {}", userId, groupId);

    // Валидация пользователя
    if (!userServiceClient.validateUser(userId)) {
      throw new InvalidUserException("User with id " + userId + " not found");
    }

    Group group = repository.findById(groupId)
        .orElseThrow(() -> new GroupNotFoundException("Group not found with id: " + groupId));

    if (group.getUsersId() == null) {
      group.setUsersId(new java.util.ArrayList<>());
    }

    if (!group.getUsersId().contains(userId)) {
      group.getUsersId().add(userId);
      log.info("User {} added to group {}", userId, groupId);
    } else {
      log.warn("User {} already in group {}", userId, groupId);
    }

    return repository.save(group);
  }

  @Transactional
  public Group removeUserFromGroup(UUID groupId, UUID userId) {
    log.info("Removing user {} from group {}", userId, groupId);

    Group group = repository.findById(groupId)
        .orElseThrow(() -> new GroupNotFoundException("Group not found with id: " + groupId));

    if (group.getUsersId() != null) {
      group.getUsersId().remove(userId);
      log.info("User {} removed from group {}", userId, groupId);
    }

    return repository.save(group);
  }

  @Transactional(readOnly = true)
  public List<Group> getAllGroups() {
    log.debug("Fetching all groups");
    return repository.findAll();
  }

  @Transactional(readOnly = true)
  public Group getGroup(UUID id) {
    log.debug("Fetching group with id: {}", id);
    return repository.findById(id)
        .orElseThrow(() -> new GroupNotFoundException("Group not found with id: " + id));
  }

  @Transactional
  public void deleteGroup(UUID id) {
    log.info("Deleting group with id: {}", id);
    if (!repository.existsById(id)) {
      throw new GroupNotFoundException("Group not found with id: " + id);
    }
    repository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public List<Group> getGroupsByCourse(UUID courseId) {
    log.debug("Fetching groups for course: {}", courseId);
    return repository.findByCourseId(courseId);
  }
}