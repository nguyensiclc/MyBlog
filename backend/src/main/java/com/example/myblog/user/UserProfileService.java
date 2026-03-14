package com.example.myblog.user;

import com.example.myblog.user.dto.UpdateProfileRequest;
import com.example.myblog.user.dto.UserProfileResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserProfileService {

    private static final List<String> ALLOWED_CONTENT_TYPES = List.of("image/jpeg", "image/png", "image/gif", "image/webp");

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final Path uploadBasePath;

    public UserProfileService(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              ObjectMapper objectMapper,
                              @Value("${app.upload-dir:./uploads}") String uploadDir) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
        this.uploadBasePath = Paths.get(uploadDir).toAbsolutePath().normalize().resolve("avatars");
    }

    public UserProfileResponse getCurrentProfile() {
        User user = getCurrentUser();
        return toResponse(user);
    }

    public UserProfileResponse updateCurrentProfile(UpdateProfileRequest request) {
        User user = getCurrentUser();

        if (request.displayName() != null) {
            user.setDisplayName(request.displayName().isBlank() ? null : request.displayName().trim());
        }
        if (request.fullName() != null) {
            user.setFullName(request.fullName().isBlank() ? null : request.fullName().trim());
        }
        if (request.email() != null) {
            user.setEmail(request.email().isBlank() ? null : request.email().trim());
        }
        if (request.phone() != null) {
            user.setPhone(request.phone().isBlank() ? null : request.phone().trim());
        }

        if (request.newPassword() != null && !request.newPassword().isBlank()) {
            if (request.currentPassword() == null || request.currentPassword().isBlank()) {
                throw new IllegalArgumentException("Current password is required to set a new password");
            }
            if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
                throw new IllegalArgumentException("Current password is incorrect");
            }
            user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        }

        if (request.settings() != null) {
            try {
                user.setSettings(objectMapper.writeValueAsString(request.settings()));
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid settings format");
            }
        }

        userRepository.save(user);
        return toResponse(user);
    }

    public UserProfileResponse uploadAvatar(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("No file provided");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Invalid file type. Allowed: JPEG, PNG, GIF, WebP");
        }
        User user = getCurrentUser();
        String ext = getExtension(file.getOriginalFilename());
        String filename = user.getId() + ext;
        try {
            Files.createDirectories(uploadBasePath);
            Path target = uploadBasePath.resolve(filename).normalize();
            try (var in = file.getInputStream()) {
                Files.copy(in, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
            user.setAvatarPath("/uploads/avatars/" + filename);
            userRepository.save(user);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to save file: " + e.getMessage());
        }
        return toResponse(user);
    }

    private static String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return ".jpg";
        return filename.substring(filename.lastIndexOf('.'));
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new SecurityException("Not authenticated");
        }
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    private UserProfileResponse toResponse(User user) {
        List<String> rolesList = user.getRoles() == null ? List.of()
                : List.of(user.getRoles().split(",")).stream()
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
        boolean admin = rolesList.stream().anyMatch(r -> r.equalsIgnoreCase("ROLE_ADMIN"));

        Map<String, Object> settingsMap = Collections.emptyMap();
        if (user.getSettings() != null && !user.getSettings().isBlank()) {
            try {
                settingsMap = objectMapper.readValue(user.getSettings(), new TypeReference<>() {});
            } catch (Exception ignored) {
            }
        }

        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getAvatarPath(),
                rolesList,
                admin,
                settingsMap,
                user.getCreatedAt()
        );
    }
}
