package com.epam.esm.repositories;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
    boolean existsByName(String name);
    Page<Tag> findByNameContaining(String partName, Pageable pageable);
}
