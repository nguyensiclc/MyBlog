package com.example.myblog.about;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "about")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class About {

    /** Single row id for the about page content. */
    @Id
    @Column(name = "id")
    private Long id = 1L;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    @Builder.Default
    private String content = "";
}
