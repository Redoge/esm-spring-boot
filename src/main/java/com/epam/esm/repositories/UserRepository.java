package com.epam.esm.repositories;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Page<User> findByUsernameContaining(String partName, Pageable pageable);
    Optional<User> findByUsername(String username);
}