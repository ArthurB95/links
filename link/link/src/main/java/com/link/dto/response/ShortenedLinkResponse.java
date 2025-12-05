package com.link.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortenedLinkResponse {
    private Long id;
    private String originalUrl;
    private String shortCode;
    private String shortUrl;
    private String customSlug;
    private Long clickCount;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
