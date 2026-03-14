package com.example.myblog.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthorId(Long authorId);

    /**
     * Example query joining user and post by author_id without FK.
     */
    @Query("""
            SELECT p FROM Post p
            JOIN com.example.myblog.user.User u
              ON p.authorId = u.id
            WHERE u.username = :username
            """)
    List<Post> findPostsByAuthorUsername(@Param("username") String username);
}

