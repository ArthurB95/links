package com.link.repository;

import com.link.entity.BioPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BioPageRepository extends JpaRepository<BioPage, Long> {
    Optional<BioPage> findByUserId(Long userId);
    Optional<BioPage> findByUserUsername(String username);
}
