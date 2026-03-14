package com.example.myblog.about.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class AboutUpdateRequest {
    @NotNull(message = "Nội dung không được để trống")
    private String content;
}
