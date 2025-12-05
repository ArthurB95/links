package com.link.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeResponse {
    private Long id;
    private String name;
    private String content;
    private Integer size;
    private String fgColor;
    private String bgColor;
    private Long scanCount;
    private LocalDateTime createdAt;
}
