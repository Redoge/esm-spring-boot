package com.epam.esm.repositories;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GiftCertificateRepositoryTest {

    @Autowired
    private GiftCertificateRepository repository;
    @Autowired
    private TagRepository tagRepository;
    private final Pageable pageable = Pageable.unpaged();

    @Test
    void findByName() {
        var gCert = repository.findByName("gCert1");

        assertTrue(gCert.isPresent());
    }

    @Test
    void findByTagsName() {
        var gCerts = repository.findByTagsName("tag1", pageable);

        assertEquals(1, gCerts.getSize());
    }

    @Test
    void findByNameContaining() {
        var gCerts = repository.findByNameContaining("gCert1", pageable);

        assertEquals(12, gCerts.getSize());
    }

    @Test
    void findByDescriptionContaining() {
        var gCerts = repository.findByDescriptionContaining("desc", pageable);

        assertEquals(100, gCerts.getSize());
    }

    @Test
    void findByNameContainingAndTagsName() {
        var gCert = repository.findByNameContainingAndTagsName("gCert1","tag1",pageable);

        assertEquals(1, gCert.getSize());
    }

    @Test
    void findByDescriptionContainingAndTagsName() {
        var gCert = repository.findByDescriptionContainingAndTagsName("description","tag1",pageable);

        assertEquals(1, gCert.getSize());
    }

    @Test
    void existsByName() {
        var gCertExists = repository.existsByName("gCert1");
        var gCertNotExists = repository.existsByName("gCert1321");

        assertTrue(gCertExists);
        assertFalse(gCertNotExists);
    }
    @Test
    void existsAll() {
        var gCerts = repository.findAll();

        assertEquals(100, gCerts.size());
    }
    @Test
    void getById() {
        var gCertExist = repository.findById(1L);
        var gCertNotExist = repository.findById(1000L);

        assertEquals("gCert1",gCertExist.get().getName());
        assertFalse(gCertNotExist.isPresent());
    }

    @BeforeAll
    public void generateData() {
        generateTags();
        generateGiftCertificates();
    }

    @Transactional
    public void generateTags(){
        var tags = new ArrayList<Tag>();
        var tagSubName = "tag";
        for(var i = 1; i<101; i++)
            tags.add(new Tag(tagSubName+i));
        tagRepository.saveAll(tags);
    }
    @Transactional
    public void generateGiftCertificates(){
        var gCerts = new ArrayList<GiftCertificate>();
        var subName = "gCert";
        var subDescription = "description";
        var price = BigDecimal.valueOf(100);
        var duration = 10;
        var date = LocalDateTime.now();
        var tags = tagRepository.findAll();
        for(var i = 1; i<101; i++){
            var gCert = new GiftCertificate();
            gCert.setName(subName+i);
            gCert.setDescription(subDescription+i);
            gCert.setDuration(duration);
            gCert.setPrice(price);
            gCert.setCreateDate(date);
            gCert.setLastUpdateDate(date);
            gCert.setTags(List.of(tags.get(i-1)));
            gCerts.add(gCert);
        }
        repository.saveAll(gCerts);
    }


}