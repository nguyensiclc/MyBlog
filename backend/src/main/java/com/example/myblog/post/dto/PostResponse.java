package com.example.myblog.post.dto;

import java.time.Instant;

public record PostResponse(
        Long id,
        String title,
        String content,
        Long authorId,
        String authorUsername,
        Instant createdAt,
        Instant updatedAt
) {
}

