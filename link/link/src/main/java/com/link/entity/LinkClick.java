package com.link.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "link_clicks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkClick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private ShortenedLink link;

    private String ipAddress;
    private String userAgent;
    private String country;
    private String city;
    private String referer;

    @Column(name = "clicked_at")
    private LocalDateTime clickedAt;
}
