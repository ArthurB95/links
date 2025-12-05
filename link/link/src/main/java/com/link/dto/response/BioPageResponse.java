package com.link.dto.response;

import com.link.enums.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BioPageResponse {
    private Long id;
    private String title;
    private String bio;
    private String avatarUrl;
    private Theme theme;
    private Boolean isPublic;
    private Long viewCount;
    private List<BioLinkResponse> links;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

