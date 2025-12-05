package com.link.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bio_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BioLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bio_page_id", nullable = false)
    private BioPage bioPage;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    private Integer position;

    @Column(name = "click_count")
    private Long clickCount = 0L;

    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
