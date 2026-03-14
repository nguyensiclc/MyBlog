package com.example.myblog.quote;

import com.example.myblog.quote.dto.QuoteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/daily")
    public ResponseEntity<QuoteResponse> getDaily() {
        return ResponseEntity.ok(quoteService.getDaily());
    }
}
