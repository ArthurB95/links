package com.link.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ShortenedLinkRequest {
    @NotBlank(message = "Original URL is required")
    @Pattern(regexp = "^https?://.*", message = "URL must start with http:// or https://")
    private String originalUrl;

    @Pattern(regexp = "^[a-z0-9-]*$", message = "Custom slug can only contain lowercase letters, numbers, and hyphens")
    private String customSlug;
}
