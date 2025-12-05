package com.link.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {
    private Long bioPageCount;
    private Long bioPageViews;
    private Long linkCount;
    private Long linkClicks;
    private Long qrCodeCount;
    private Long qrCodeScans;
    private Long totalClicks;
}
