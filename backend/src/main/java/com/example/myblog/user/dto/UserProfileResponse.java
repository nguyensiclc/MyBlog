package com.example.myblog.user.dto;

import java.util.List;
import java.util.Map;

public record UserProfileResponse(
        Long id,
        String username,
        String displayName,
        String fullName,
        String email,
        String phone,
        String avatarPath,
        List<String> roles,
        boolean admin,
        Map<String, Object> settings,
        java.time.Instant createdAt
) {}
