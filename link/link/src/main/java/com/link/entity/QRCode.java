package com.link.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "qr_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 2048)
    private String content; // URL ou texto

    private Integer size = 256;

    @Column(name = "fg_color")
    private String fgColor = "#000000";

    @Column(name = "bg_color")
    private String bgColor = "#ffffff";

    @Column(name = "scan_count")
    private Long scanCount = 0L;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
