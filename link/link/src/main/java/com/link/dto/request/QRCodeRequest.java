package com.link.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class QRCodeRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Content is required")
    private String content;

    @Min(value = 128, message = "Size must be at least 128")
    @Max(value = 1024, message = "Size must not exceed 1024")
    private Integer size = 256;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Foreground color must be a valid hex color")
    private String fgColor = "#000000";

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Background color must be a valid hex color")
    private String bgColor = "#ffffff";
}
