package com.link.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shortened_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenedLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(unique = true, nullable = false)
    private String shortCode; // código curto: "abc123"

    private String customSlug; // slug personalizado

    @Column(name = "click_count")
    private Long clickCount = 0L;

    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt; // opcional

    @OneToMany(mappedBy = "link", cascade = CascadeType.ALL)
    private List<LinkClick> clicks;
}
