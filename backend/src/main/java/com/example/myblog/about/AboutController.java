package com.example.myblog.about;

import com.example.myblog.about.dto.AboutResponse;
import com.example.myblog.about.dto.AboutUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/about")
public class AboutController {

    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
    public ResponseEntity<AboutResponse> getAbout() {
        return ResponseEntity.ok(aboutService.getContent());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AboutResponse> updateAbout(@Valid @RequestBody AboutUpdateRequest request) {
        return ResponseEntity.ok(aboutService.updateContent(request));
    }
}
