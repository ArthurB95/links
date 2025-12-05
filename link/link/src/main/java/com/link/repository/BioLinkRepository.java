package com.link.repository;

import com.link.entity.BioLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BioLinkRepository extends JpaRepository<BioLink, Long> {
    List<BioLink> findByBioPageIdOrderByPositionAsc(Long bioPageId);
}
