package com.link.repository;

import com.link.entity.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QRCodeRepository extends JpaRepository<QRCode, Long> {
    List<QRCode> findByUserId(Long userId);
    Long countByUserId(Long userId);
}
