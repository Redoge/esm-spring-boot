package com.epam.esm.repositories;

import com.epam.esm.entities.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
    Optional<GiftCertificate> findByName(String name);
    Page<GiftCertificate> findByTagsName(String tagName, Pageable pageable);
    Page<GiftCertificate> findByNameContaining(String name, Pageable pageable);
    Page<GiftCertificate> findByDescriptionContaining(String description, Pageable pageable);
    Page<GiftCertificate> findByNameContainingAndTagsName(String name, String tagName, Pageable pageable);
    Page<GiftCertificate> findByDescriptionContainingAndTagsName(String name, String tagName, Pageable pageable);
    boolean existsByName(String name);
    boolean existsById(long id);

}
