package com.example.myblog.topic;

import com.example.myblog.topic.dto.TopicRequest;
import com.example.myblog.topic.dto.TopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<TopicResponse> getTree() {
        List<Topic> all = topicRepository.findAllByOrderBySortOrderAscIdAsc();
        List<Topic> roots = all.stream().filter(t -> t.getParentId() == null).toList();
        List<Topic> children = all.stream().filter(t -> t.getParentId() != null).toList();
        Map<Long, List<Topic>> byParent = children.stream().collect(Collectors.groupingBy(Topic::getParentId));

        return roots.stream()
                .map(r -> toResponse(r, byParent.getOrDefault(r.getId(), List.of())))
                .toList();
    }

    private TopicResponse toResponse(Topic t, List<Topic> childList) {
        TopicResponse resp = TopicResponse.builder()
                .id(t.getId())
                .label(t.getLabel())
                .target(t.getTarget())
                .sortOrder(t.getSortOrder() != null ? t.getSortOrder() : 0)
                .parentId(t.getParentId())
                .build();
        resp.setChildren(childList.stream()
                .map(c -> toResponse(c, List.of()))
                .toList());
        return resp;
    }

    @Transactional
    public TopicResponse create(TopicRequest request) {
        if (request.getParentId() != null) {
            Topic parent = topicRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Chủ đề cha không tồn tại"));
            if (parent.getParentId() != null) {
                throw new IllegalArgumentException("Chỉ hỗ trợ tối đa 2 cấp chủ đề");
            }
        }
        Topic topic = Topic.builder()
                .label(request.getLabel())
                .target(request.getTarget())
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0)
                .parentId(request.getParentId())
                .build();
        topic = topicRepository.save(topic);
        return toResponse(topic, List.of());
    }

    @Transactional
    public TopicResponse update(Long id, TopicRequest request) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Chủ đề không tồn tại"));
        if (request.getParentId() != null) {
            if (request.getParentId().equals(id)) {
                throw new IllegalArgumentException("Chủ đề không thể là cha của chính nó");
            }
            Topic parent = topicRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Chủ đề cha không tồn tại"));
            if (parent.getParentId() != null) {
                throw new IllegalArgumentException("Chỉ hỗ trợ tối đa 2 cấp chủ đề");
            }
        }
        topic.setLabel(request.getLabel());
        topic.setTarget(request.getTarget());
        topic.setSortOrder(request.getSortOrder() != null ? request.getSortOrder() : topic.getSortOrder());
        topic.setParentId(request.getParentId());
        topic = topicRepository.save(topic);
        List<Topic> children = topicRepository.findByParentIdOrderBySortOrderAscIdAsc(topic.getId());
        return toResponse(topic, children);
    }

    @Transactional
    public void delete(Long id) {
        if (!topicRepository.existsById(id)) {
            throw new IllegalArgumentException("Chủ đề không tồn tại");
        }
        topicRepository.deleteByParentId(id);
        topicRepository.deleteById(id);
    }
}
