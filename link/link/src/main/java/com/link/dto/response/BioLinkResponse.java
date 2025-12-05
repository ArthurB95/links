package com.link.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BioLinkResponse {
    private Long id;
    private String title;
    private String url;
    private Integer position;
    private Long clickCount;
    private Boolean isActive;
}
