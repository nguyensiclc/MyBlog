package com.example.myblog.topic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TopicRequest {

    @NotBlank(message = "Nhãn không được để trống")
    @Size(max = 120)
    private String label;

    @NotBlank(message = "Đích đến không được để trống")
    @Size(max = 255)
    private String target;

    private Integer sortOrder;

    /** Null = cấp 1; set id cha = cấp 2 (tối đa 2 cấp). */
    private Long parentId;
}
