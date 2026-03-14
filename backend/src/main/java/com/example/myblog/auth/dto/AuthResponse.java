package com.example.myblog.auth.dto;

import java.util.List;

public record AuthResponse(
        String accessToken,
        String tokenType,
        String username,
        List<String> roles,
        boolean isAdmin
) {
    public static AuthResponse from(String token, String username, List<String> roles) {
        boolean isAdmin = roles.stream().anyMatch(r -> r.equalsIgnoreCase("ROLE_ADMIN"));
        return new AuthResponse(token, "Bearer", username, roles, isAdmin);
    }
}

