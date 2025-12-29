package com.link.controller;

import com.link.dto.response.DashboardStatsResponse;
import com.link.service.DashboardService;
import com.link.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsResponse> getStats(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        DashboardStatsResponse stats = dashboardService.getStats(userPrincipal.getId());
        return ResponseEntity.ok(stats);
    }
}
