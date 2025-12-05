package com.link.entity;

import com.link.enums.Theme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bio_pages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BioPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false)
    private String title; // Nome/título do perfil

    @Column(length = 500)
    private String bio;

    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private Theme theme;

    private Boolean isPublic = true;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @OneToMany(mappedBy = "bioPage", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private List<BioLink> links;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
