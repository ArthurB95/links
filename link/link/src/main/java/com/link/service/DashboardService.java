package com.link.service;

import com.link.dto.response.DashboardStatsResponse;
import com.link.entity.BioPage;
import com.link.repository.BioPageRepository;
import com.link.repository.QRCodeRepository;
import com.link.repository.ShortenedLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final BioPageRepository bioPageRepository;
    private final ShortenedLinkRepository linkRepository;
    private final QRCodeRepository qrCodeRepository;

    @Transactional(readOnly = true)
    public DashboardStatsResponse getStats(Long userId) {
        DashboardStatsResponse stats = new DashboardStatsResponse();

        BioPage bioPage = bioPageRepository.findByUserId(userId).orElse(null);
        if (bioPage != null) {
            stats.setBioPageCount(1L);
            stats.setBioPageViews(bioPage.getViewCount());

            long bioLinkClicks = bioPage.getLinks().stream()
                    .mapToLong(link -> link.getClickCount())
                    .sum();

            // Link stats
            Long linkCount = linkRepository.countByUserId(userId);
            stats.setLinkCount(linkCount);

            long linkClicks = linkRepository.findByUserId(userId, org.springframework.data.domain.Pageable.unpaged())
                    .stream()
                    .mapToLong(link -> link.getClickCount())
                    .sum();
            stats.setLinkClicks(linkClicks);

            Long qrCodeCount = qrCodeRepository.countByUserId(userId);
            stats.setQrCodeCount(qrCodeCount);

            long qrCodeScans = qrCodeRepository.findByUserId(userId).stream()
                    .mapToLong(qr -> qr.getScanCount())
                    .sum();
            stats.setQrCodeScans(qrCodeScans);

            stats.setTotalClicks(bioLinkClicks + linkClicks + qrCodeScans);
        } else {
            stats.setBioPageCount(0L);
            stats.setBioPageViews(0L);
            stats.setLinkCount(0L);
            stats.setLinkClicks(0L);
            stats.setQrCodeCount(0L);
            stats.setQrCodeScans(0L);
            stats.setTotalClicks(0L);
        }

        return stats;
    }
}
