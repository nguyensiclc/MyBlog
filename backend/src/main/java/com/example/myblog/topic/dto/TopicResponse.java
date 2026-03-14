package com.example.myblog.topic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicResponse {
    private Long id;
    private String label;
    private String target;
    private Integer sortOrder;
    private Long parentId;
    @Builder.Default
    private List<TopicResponse> children = new ArrayList<>();
}
