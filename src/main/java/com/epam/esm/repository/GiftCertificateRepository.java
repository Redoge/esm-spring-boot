package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
    Optional<GiftCertificate> findByName(String name);

    List<GiftCertificate> findByTagsName(String tagName);
    List<GiftCertificate> findByNameContaining(String name);
    List<GiftCertificate> findByDescriptionContaining(String description);

    List<GiftCertificate> findByNameContainingAndTagsName(String name, String tagName);

    List<GiftCertificate> findByDescriptionContainingAndTagsName(String name, String tagName);
}