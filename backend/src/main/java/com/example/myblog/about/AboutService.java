package com.example.myblog.about;

import com.example.myblog.about.dto.AboutResponse;
import com.example.myblog.about.dto.AboutUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AboutService {

    private static final Long ABOUT_ID = 1L;

    private final AboutRepository aboutRepository;

    @Transactional(readOnly = true)
    public AboutResponse getContent() {
        return aboutRepository.findById(ABOUT_ID)
                .map(a -> AboutResponse.builder().content(a.getContent()).build())
                .orElse(AboutResponse.builder().content("").build());
    }

    @Transactional
    public AboutResponse updateContent(AboutUpdateRequest request) {
        About about = aboutRepository.findById(ABOUT_ID)
                .orElseGet(() -> About.builder().id(ABOUT_ID).content("").build());
        about.setContent(request.getContent() != null ? request.getContent() : "");
        about = aboutRepository.save(about);
        return AboutResponse.builder().content(about.getContent()).build();
    }
}
