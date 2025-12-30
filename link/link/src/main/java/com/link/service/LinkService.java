package com.link.service;

import com.link.dto.request.ShortenedLinkRequest;
import com.link.dto.response.ShortenedLinkResponse;
import com.link.entity.LinkClick;
import com.link.entity.ShortenedLink;
import com.link.entity.User;
import com.link.repository.LinkClickRepository;
import com.link.repository.ShortenedLinkRepository;
import com.link.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final ShortenedLinkRepository linkRepository;
    private final UserRepository userRepository;
    private final LinkClickRepository clickRepository;

    @Value("${app.link.base-url:link.io}")
    private String baseUrl;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;
    private static final Random random = new SecureRandom();

    @Transactional(readOnly = true)
    public Page<ShortenedLinkResponse> getMyLinks(Long userId, Pageable pageable) {
        Page<ShortenedLink> links = linkRepository.findByUserId(userId, pageable);
        return links.map(this::mapToResponse);
    }

    @Transactional
    public ShortenedLinkResponse createLink(Long userId, ShortenedLinkRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String shortCode;
        if (request.getCustomSlug() != null && !request.getCustomSlug().isEmpty()) {
            if (linkRepository.existsByCustomSlug(request.getCustomSlug()) ||
                    linkRepository.existsByShortCode(request.getCustomSlug())) {
                throw new RuntimeException("Custom slug already exists");
            }
            shortCode = request.getCustomSlug();
        } else {
            shortCode = generateShortCode();
        }

        ShortenedLink link = new ShortenedLink();
        link.setUser(user);
        link.setOriginalUrl(request.getOriginalUrl());
        link.setShortCode(shortCode);
        link.setCustomSlug(request.getCustomSlug());
        link.setClickCount(0L);
        link.setIsActive(true);

        link = linkRepository.save(link);
        return mapToResponse(link);
    }

    @Transactional(readOnly = true)
    public ShortenedLinkResponse getLinkById(Long userId, Long linkId) {
        ShortenedLink link = linkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        if (!link.getUser().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to view this link");
        }

        return mapToResponse(link);
    }

    @Transactional
    public void deleteLink(Long userId, Long linkId) {
        ShortenedLink link = linkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        if (!link.getUser().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to delete this link");
        }

        linkRepository.delete(link);
    }

    @Transactional
    public String redirectLink(String shortCode, String ipAddress, String userAgent, String referer) {
        ShortenedLink link = linkRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        if (!link.getIsActive()) {
            throw new RuntimeException("Link is inactive");
        }

        link.setClickCount(link.getClickCount() + 1);
        linkRepository.save(link);

        LinkClick click = new LinkClick();
        click.setLink(link);
        click.setIpAddress(ipAddress);
        click.setUserAgent(userAgent);
        click.setReferer(referer);
        clickRepository.save(click);

        return link.getOriginalUrl();
    }

    private String generateShortCode() {
        String shortCode;
        do {
            shortCode = generateRandomString(SHORT_CODE_LENGTH);
        } while (linkRepository.existsByShortCode(shortCode));
        return shortCode;
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private ShortenedLinkResponse mapToResponse(ShortenedLink link) {
        ShortenedLinkResponse response = new ShortenedLinkResponse();
        response.setId(link.getId());
        response.setOriginalUrl(link.getOriginalUrl());
        response.setShortCode(link.getShortCode());
        response.setShortUrl(baseUrl + "/" + link.getShortCode());
        response.setCustomSlug(link.getCustomSlug());
        response.setClickCount(link.getClickCount());
        response.setIsActive(link.getIsActive());
        response.setCreatedAt(link.getCreatedAt());
        return response;
    }
}
