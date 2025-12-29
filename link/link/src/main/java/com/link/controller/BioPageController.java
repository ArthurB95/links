package com.link.controller;

import com.link.dto.request.BioLinkRequest;
import com.link.dto.request.BioPageRequest;
import com.link.dto.request.ReorderLinksRequest;
import com.link.dto.response.BioLinkResponse;
import com.link.dto.response.BioPageResponse;
import com.link.service.BioPageService;
import com.link.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BioPageController {
    private final BioPageService bioPageService;

    @GetMapping("/bio-pages/my")
    public ResponseEntity<BioPageResponse> getMyBioPage(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        BioPageResponse bioPage = bioPageService.getMyBioPage(userPrincipal.getId());
        return ResponseEntity.ok(bioPage);
    }

    @PutMapping("/bio-pages/my")
    public ResponseEntity<BioPageResponse> updateMyBioPage(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody BioPageRequest request) {
        BioPageResponse bioPage = bioPageService.updateMyBioPage(userPrincipal.getId(), request);
        return ResponseEntity.ok(bioPage);
    }

    @PostMapping("/bio-pages/my/links")
    public ResponseEntity<BioLinkResponse> addLink(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody BioLinkRequest request) {
        BioLinkResponse link = bioPageService.addLink(userPrincipal.getId(), request);
        return ResponseEntity.ok(link);
    }

    @PutMapping("/bio-pages/my/links/{linkId}")
    public ResponseEntity<BioLinkResponse> updateLink(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long linkId,
            @Valid @RequestBody BioLinkRequest request) {
        BioLinkResponse link = bioPageService.updateLink(userPrincipal.getId(), linkId, request);
        return ResponseEntity.ok(link);
    }

    @DeleteMapping("/bio-pages/my/links/{linkId}")
    public ResponseEntity<Void> deleteLink(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long linkId) {
        bioPageService.deleteLink(userPrincipal.getId(), linkId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/bio-pages/my/links/reorder")
    public ResponseEntity<Void> reorderLinks(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ReorderLinksRequest request) {
        bioPageService.reorderLinks(userPrincipal.getId(), request.getLinkIds());
        return ResponseEntity.ok().build();
    }


    @GetMapping("/public/bio-pages/{username}")
    public ResponseEntity<BioPageResponse> getPublicBioPage(@PathVariable String username) {
        bioPageService.incrementViewCount(username);
        BioPageResponse bioPage = bioPageService.getPublicBioPage(username);
        return ResponseEntity.ok(bioPage);
    }

    @PostMapping("/public/bio-pages/{username}/links/{linkId}/click")
    public ResponseEntity<Void> registerLinkClick(@PathVariable Long linkId) {
        bioPageService.registerLinkClick(linkId);
        return ResponseEntity.ok().build();
    }
}
