package com.link.dto.request;

import com.link.enums.Theme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class BioPageRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @Size(max = 500, message = "Bio must be less than 500 characters")
    private String bio;

    private String avatarUrl;

    @NotNull(message = "Theme is required")
    private Theme theme;

    private Boolean isPublic = true;
}
