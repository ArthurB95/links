package com.link.repository;

import com.link.entity.ShortenedLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortenedLinkRepository extends JpaRepository<ShortenedLink, Long> {
    Optional<ShortenedLink> findByShortCode(String shortCode);
    Page<ShortenedLink> findByUserId(Long userId, Pageable pageable);
    Boolean existsByShortCode(String shortCode);
    Boolean existsByCustomSlug(String customSlug);
    Long countByUserId(Long userId);
}
