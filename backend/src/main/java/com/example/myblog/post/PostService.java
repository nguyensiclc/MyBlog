package com.example.myblog.post;

import com.example.myblog.post.dto.PostRequest;
import com.example.myblog.post.dto.PostResponse;
import com.example.myblog.user.User;
import com.example.myblog.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return mapToResponsesWithJoin(posts);
    }

    public List<PostResponse> getPostsByAuthorUsername(String username) {
        List<Post> posts = postRepository.findPostsByAuthorUsername(username);
        return mapToResponsesWithJoin(posts);
    }

    public PostResponse createPost(PostRequest request) {
        User currentUser = getCurrentUser();

        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .authorId(currentUser.getId())
                .build();

        Post saved = postRepository.save(post);
        return mapToResponse(saved, currentUser);
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        User currentUser = getCurrentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getAuthorId().equals(currentUser.getId())) {
            throw new SecurityException("You are not allowed to edit this post");
        }

        post.setTitle(request.title());
        post.setContent(request.content());

        Post saved = postRepository.save(post);
        return mapToResponse(saved, currentUser);
    }

    public void deletePost(Long id) {
        User currentUser = getCurrentUser();
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getAuthorId().equals(currentUser.getId())) {
            throw new SecurityException("You are not allowed to delete this post");
        }

        postRepository.delete(post);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new SecurityException("Not authenticated");
        }

        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Current user not found"));
    }

    private List<PostResponse> mapToResponsesWithJoin(List<Post> posts) {
        List<Long> authorIds = posts.stream()
                .map(Post::getAuthorId)
                .distinct()
                .toList();

        List<User> authors = userRepository.findAllById(authorIds);

        return posts.stream()
                .map(post -> {
                    User author = authors.stream()
                            .filter(u -> u.getId().equals(post.getAuthorId()))
                            .findFirst()
                            .orElse(null);
                    String username = author != null ? author.getUsername() : null;
                    return mapToResponse(post, author, username);
                })
                .collect(Collectors.toList());
    }

    private PostResponse mapToResponse(Post post, User author) {
        String username = author != null ? author.getUsername() : null;
        return mapToResponse(post, author, username);
    }

    private PostResponse mapToResponse(Post post, User author, String username) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthorId(),
                username,
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}

