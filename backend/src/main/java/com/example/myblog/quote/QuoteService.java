package com.example.myblog.quote;

import com.example.myblog.quote.dto.QuoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;

    /**
     * Trả về một câu quote cố định cho cả ngày (cùng ngày = cùng quote).
     */
    @Transactional(readOnly = true)
    public QuoteResponse getDaily() {
        long count = quoteRepository.count();
        if (count == 0) {
            return QuoteResponse.builder()
                    .content("Góc nhỏ chia sẻ ý tưởng, code và cuộc sống làm dev.")
                    .author(null)
                    .build();
        }
        long dayIndex = Math.floorMod(LocalDate.now().toEpochDay(), count);
        Quote q = quoteRepository.findOneAtOffset((int) dayIndex);
        if (q == null) {
            q = quoteRepository.findAllByOrderByIdAsc().get(0);
        }
        return QuoteResponse.builder()
                .content(q.getContent())
                .author(q.getAuthor())
                .build();
    }
}
