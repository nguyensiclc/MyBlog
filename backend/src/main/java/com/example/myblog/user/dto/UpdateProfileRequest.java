package com.example.myblog.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateProfileRequest(
        @Size(max = 100) String displayName,
        @Size(max = 150) String fullName,
        @Size(max = 150) String email,
        @Size(max = 20) String phone,
        String currentPassword,
        @Size(min = 6, max = 100) String newPassword,
        Map<String, Object> settings
) {}
