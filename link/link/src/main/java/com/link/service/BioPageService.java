package com.link.service;

import com.link.dto.request.BioLinkRequest;
import com.link.dto.request.BioPageRequest;
import com.link.dto.response.BioLinkResponse;
import com.link.dto.response.BioPageResponse;
import com.link.entity.BioLink;
import com.link.entity.BioPage;
import com.link.entity.User;
import com.link.repository.BioLinkRepository;
import com.link.repository.BioPageRepository;
import com.link.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BioPageService {
    private final BioPageRepository bioPageRepository;
    private final BioLinkRepository bioLinkRepository;
    private final UserRepository userRepository;

    @Transactional
    public BioPageResponse getMyBioPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BioPage bioPage = bioPageRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultBioPage(user));

        return mapToResponse(bioPage);
    }

    @Transactional
    public BioPageResponse updateMyBioPage(Long userId, BioPageRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BioPage bioPage = bioPageRepository.findByUserId(userId)
                .orElseGet(() -> createDefaultBioPage(user));

        bioPage.setTitle(request.getTitle());
        bioPage.setBio(request.getBio());
        bioPage.setAvatarUrl(request.getAvatarUrl());
        bioPage.setTheme(request.getTheme());
        bioPage.setIsPublic(request.getIsPublic());

        bioPage = bioPageRepository.save(bioPage);
        return mapToResponse(bioPage);
    }

    @Transactional
    public BioLinkResponse addLink(Long userId, BioLinkRequest request) {
        BioPage bioPage = bioPageRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bio page not found"));

        BioLink link = new BioLink();
        link.setBioPage(bioPage);
        link.setTitle(request.getTitle());
        link.setUrl(request.getUrl());

        int currentSize = (bioPage.getLinks() == null) ? 0 : bioPage.getLinks().size();
        link.setPosition(request.getPosition() != null ? request.getPosition() : currentSize);

        link.setIsActive(true);
        link.setClickCount(0L);

        link = bioLinkRepository.save(link);
        return mapToLinkDTO(link);
    }

    @Transactional
    public BioLinkResponse updateLink(Long userId, Long linkId, BioLinkRequest request) {
        BioLink link = bioLinkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        if (!link.getBioPage().getUser().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to update this link");
        }

        link.setTitle(request.getTitle());
        link.setUrl(request.getUrl());
        if (request.getPosition() != null) {
            link.setPosition(request.getPosition());
        }

        link = bioLinkRepository.save(link);
        return mapToLinkDTO(link);
    }

    @Transactional
    public void deleteLink(Long userId, Long linkId) {
        BioLink link = bioLinkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        if (!link.getBioPage().getUser().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to delete this link");
        }

        bioLinkRepository.delete(link);
    }

    @Transactional
    public void reorderLinks(Long userId, List<Long> linkIds) {
        BioPage bioPage = bioPageRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Bio page not found"));

        for (int i = 0; i < linkIds.size(); i++) {
            Long linkId = linkIds.get(i);
            BioLink link = bioLinkRepository.findById(linkId)
                    .orElseThrow(() -> new RuntimeException("Link not found: " + linkId));

            if (!link.getBioPage().getId().equals(bioPage.getId())) {
                throw new RuntimeException("Link does not belong to this bio page");
            }

            link.setPosition(i);
            bioLinkRepository.save(link);
        }
    }

    @Transactional(readOnly = true)
    public BioPageResponse getPublicBioPage(String username) {
        BioPage bioPage = bioPageRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Bio page not found"));

        if (!bioPage.getIsPublic()) {
            throw new RuntimeException("Bio page is private");
        }

        return mapToResponse(bioPage);
    }

    @Transactional
    public void incrementViewCount(String username) {
        BioPage bioPage = bioPageRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Bio page not found"));

        bioPage.setViewCount(bioPage.getViewCount() + 1);
        bioPageRepository.save(bioPage);
    }

    @Transactional
    public void registerLinkClick(Long linkId) {
        BioLink link = bioLinkRepository.findById(linkId)
                .orElseThrow(() -> new RuntimeException("Link not found"));

        link.setClickCount(link.getClickCount() + 1);
        bioLinkRepository.save(link);
    }

    private BioPage createDefaultBioPage(User user) {
        BioPage bioPage = new BioPage();
        bioPage.setUser(user);
        bioPage.setTitle(user.getName());
        bioPage.setBio("");
        bioPage.setAvatarUrl(user.getAvatar());
        bioPage.setIsPublic(true);
        bioPage.setViewCount(0L);
        return bioPageRepository.save(bioPage);
    }

    private BioPageResponse mapToResponse(BioPage bioPage) {
        BioPageResponse response = new BioPageResponse();
        response.setId(bioPage.getId());
        response.setTitle(bioPage.getTitle());
        response.setBio(bioPage.getBio());
        response.setAvatarUrl(bioPage.getAvatarUrl());
        response.setTheme(bioPage.getTheme());
        response.setIsPublic(bioPage.getIsPublic());
        response.setViewCount(bioPage.getViewCount());
        response.setCreatedAt(bioPage.getCreatedAt());
        response.setUpdatedAt(bioPage.getUpdatedAt());

        List<BioLinkResponse> links = (bioPage.getLinks() == null) ?
                Collections.emptyList() :
                bioPage.getLinks().stream()
                        .map(this::mapToLinkDTO)
                        .collect(Collectors.toList());

        response.setLinks(links);

        return response;
    }

    private BioLinkResponse mapToLinkDTO(BioLink link) {
        BioLinkResponse dto = new BioLinkResponse();
        dto.setId(link.getId());
        dto.setTitle(link.getTitle());
        dto.setUrl(link.getUrl());
        dto.setPosition(link.getPosition());
        dto.setClickCount(link.getClickCount());
        dto.setIsActive(link.getIsActive());
        return dto;
    }
}
