package com.example.myblog.topic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAllByOrderBySortOrderAscIdAsc();

    List<Topic> findByParentIdIsNullOrderBySortOrderAscIdAsc();

    List<Topic> findByParentIdOrderBySortOrderAscIdAsc(Long parentId);

    @Modifying
    @Query("DELETE FROM Topic t WHERE t.parentId = :id")
    void deleteByParentId(@Param("id") Long id);
}
