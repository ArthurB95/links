package com.link.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.link.dto.request.QRCodeRequest;
import com.link.dto.response.QRCodeResponse;
import com.link.entity.QRCode;
import com.link.entity.User;
import com.link.repository.QRCodeRepository;
import com.link.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QRCodeService {
    private final QRCodeRepository qrCodeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<QRCodeResponse> getMyQRCodes(Long userId) {
        List<QRCode> qrCodes = qrCodeRepository.findByUserId(userId);
        return qrCodes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public QRCodeResponse createQRCode(Long userId, QRCodeRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        QRCode qrCode = new QRCode();
        qrCode.setUser(user);
        qrCode.setName(request.getName());
        qrCode.setContent(request.getContent());
        qrCode.setSize(request.getSize());
        qrCode.setFgColor(request.getFgColor());
        qrCode.setBgColor(request.getBgColor());
        qrCode.setScanCount(0L);

        qrCode = qrCodeRepository.save(qrCode);
        return mapToResponse(qrCode);
    }

    @Transactional(readOnly = true)
    public QRCodeResponse getQRCodeById(Long userId, Long qrCodeId) {
        QRCode qrCode = qrCodeRepository.findById(qrCodeId)
                .orElseThrow(() -> new RuntimeException("QR Code not found"));

        if (!qrCode.getUser().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to view this QR code");
        }

        return mapToResponse(qrCode);
    }

    @Transactional
    public void deleteQRCode(Long userId, Long qrCodeId) {
        QRCode qrCode = qrCodeRepository.findById(qrCodeId)
                .orElseThrow(() -> new RuntimeException("QR Code not found"));

        if (!qrCode.getUser().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to delete this QR code");
        }

        qrCodeRepository.delete(qrCode);
    }

    @Transactional
    public void incrementScanCount(Long qrCodeId) {
        QRCode qrCode = qrCodeRepository.findById(qrCodeId)
                .orElseThrow(() -> new RuntimeException("QR Code not found"));

        qrCode.setScanCount(qrCode.getScanCount() + 1);
        qrCodeRepository.save(qrCode);
    }

    public String generateQRCodeImage(String content, int size, String fgColor, String bgColor) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size);

            int fg = Integer.parseInt(fgColor.substring(1), 16);
            int bg = Integer.parseInt(bgColor.substring(1), 16);

            MatrixToImageConfig config = new MatrixToImageConfig(fg, bg);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, config);

            byte[] imageBytes = outputStream.toByteArray();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Failed to generate QR code: " + e.getMessage());
        }
    }

    private QRCodeResponse mapToResponse(QRCode qrCode) {
        QRCodeResponse response = new QRCodeResponse();
        response.setId(qrCode.getId());
        response.setName(qrCode.getName());
        response.setContent(qrCode.getContent());
        response.setSize(qrCode.getSize());
        response.setFgColor(qrCode.getFgColor());
        response.setBgColor(qrCode.getBgColor());
        response.setScanCount(qrCode.getScanCount());
        response.setCreatedAt(qrCode.getCreatedAt());
        return response;
    }
}
