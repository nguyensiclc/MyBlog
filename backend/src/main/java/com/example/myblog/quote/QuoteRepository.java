package com.example.myblog.quote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findAllByOrderByIdAsc();

    @Query(value = "SELECT * FROM quotes ORDER BY id LIMIT 1 OFFSET :offset", nativeQuery = true)
    Quote findOneAtOffset(@Param("offset") int offset);

    long count();
}
