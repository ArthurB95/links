package com.link.controller;

import com.link.dto.request.QRCodeRequest;
import com.link.dto.response.QRCodeResponse;
import com.link.service.QRCodeService;
import com.link.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/qr-codes")
@RequiredArgsConstructor
public class QRCodeController {
    private final QRCodeService qrCodeService;

    @GetMapping
    public ResponseEntity<List<QRCodeResponse>> getMyQRCodes(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<QRCodeResponse> qrCodes = qrCodeService.getMyQRCodes(userPrincipal.getId());
        return ResponseEntity.ok(qrCodes);
    }

    @PostMapping
    public ResponseEntity<QRCodeResponse> createQRCode(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody QRCodeRequest request) {
        QRCodeResponse qrCode = qrCodeService.createQRCode(userPrincipal.getId(), request);
        return ResponseEntity.ok(qrCode);
    }

    @GetMapping("/{qrCodeId}")
    public ResponseEntity<QRCodeResponse> getQRCodeById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long qrCodeId) {
        QRCodeResponse qrCode = qrCodeService.getQRCodeById(userPrincipal.getId(), qrCodeId);
        return ResponseEntity.ok(qrCode);
    }

    @DeleteMapping("/{qrCodeId}")
    public ResponseEntity<Void> deleteQRCode(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long qrCodeId) {
        qrCodeService.deleteQRCode(userPrincipal.getId(), qrCodeId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{qrCodeId}/scan")
    public ResponseEntity<Void> registerScan(@PathVariable Long qrCodeId) {
        qrCodeService.incrementScanCount(qrCodeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{qrCodeId}/image")
    public ResponseEntity<String> generateQRCodeImage(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long qrCodeId) {
        QRCodeResponse qrCode = qrCodeService.getQRCodeById(userPrincipal.getId(), qrCodeId);
        String imageData = qrCodeService.generateQRCodeImage(
                qrCode.getContent(),
                qrCode.getSize(),
                qrCode.getFgColor(),
                qrCode.getBgColor()
        );
        return ResponseEntity.ok(imageData);
    }
}
