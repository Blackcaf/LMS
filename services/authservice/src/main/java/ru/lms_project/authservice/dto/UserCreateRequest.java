package ru.lms_project.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;

    private String role;
    private Boolean active;
}
