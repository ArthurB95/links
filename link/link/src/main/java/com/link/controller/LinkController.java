package com.link.controller;

import com.link.dto.request.ShortenedLinkRequest;
import com.link.dto.response.ShortenedLinkResponse;
import com.link.security.UserPrincipal;
import com.link.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @GetMapping("/links")
    public ResponseEntity<Page<ShortenedLinkResponse>> getMyLinks(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Pageable pageable) {
        Page<ShortenedLinkResponse> links = linkService.getMyLinks(userPrincipal.getId(), pageable);
        return ResponseEntity.ok(links);
    }

    @PostMapping("/links")
    public ResponseEntity<ShortenedLinkResponse> createLink(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody ShortenedLinkRequest request) {
        ShortenedLinkResponse link = linkService.createLink(userPrincipal.getId(), request);
        return ResponseEntity.ok(link);
    }

    @GetMapping("/links/{linkId}")
    public ResponseEntity<ShortenedLinkResponse> getLinkById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long linkId) {
        ShortenedLinkResponse link = linkService.getLinkById(userPrincipal.getId(), linkId);
        return ResponseEntity.ok(link);
    }

    @DeleteMapping("/links/{linkId}")
    public ResponseEntity<Void> deleteLink(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long linkId) {
        linkService.deleteLink(userPrincipal.getId(), linkId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public/links/{shortCode}")
    public ResponseEntity<Void> redirectLink(
            @PathVariable String shortCode,
            HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");

        String originalUrl = linkService.redirectLink(shortCode, ipAddress, userAgent, referer);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
