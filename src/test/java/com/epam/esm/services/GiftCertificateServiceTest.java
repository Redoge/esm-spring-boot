package com.epam.esm.services;


import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.repositories.GiftCertificateRepository;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.util.mappers.GiftCertificateMapper;
import com.epam.esm.util.validators.GiftCertificateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.services.TagServiceTest.getTestTags;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {
    @Mock
    private  GiftCertificateRepository giftCertificateDao;
    @Mock
    private  GiftCertificateMapper giftCertificateMapper;
    @Mock
    private  GiftCertificateValidator giftCertificateValidator;
    @Mock
    private  TagService tagService;
    @Mock
    private  UserRepository userRepository;
    @InjectMocks
    private GiftCertificateService giftCertificateService;
    private final Long testId = 1L;
    private final Long testIdIncorrect = 100L;
    private final String testName = "name1";
    private final String testNameIncorrect = "name100";
    private final List<GiftCertificate> testGCerts = getGiftCertificates();

    @Test
    void getAllTest(){
        when(giftCertificateDao.findAll()).thenReturn(testGCerts);

        var actualGCerts = giftCertificateService.getAll();

        assertEquals(testGCerts, actualGCerts);
    }

    @Test
    void getAllPageableTest(){
        var pageable = Pageable.unpaged();
        var gCertsPage = new PageImpl<>(testGCerts);

        when(giftCertificateDao.findAll(pageable)).thenReturn(gCertsPage);

        var actualGCerts = giftCertificateService.getAll(pageable);

        assertEquals(gCertsPage, actualGCerts);
    }
    @Test
    void getAllByPartName(){
        var pageable = Pageable.unpaged();
        var gCertsPage = new PageImpl<>(testGCerts);

        when(giftCertificateDao.findByNameContaining(testName,pageable)).thenReturn(gCertsPage);

        var actualGCerts = giftCertificateService.getByPartName(testName, pageable);

        assertEquals(gCertsPage, actualGCerts);
    }
    @Test
    void getByIdTest() throws ObjectNotFoundException {
        when(giftCertificateDao.findById(testId)).thenReturn(Optional.of(testGCerts.get(0)));
        when(giftCertificateDao.findById(testIdIncorrect)).thenReturn(Optional.empty());

        var gCert = giftCertificateService.getById(testId);

        assertEquals(testGCerts.get(0), gCert.get());

        assertThrows(ObjectNotFoundException.class, ()->giftCertificateService.getById(testIdIncorrect));

    }
    @Test
    void getByNameTest() throws ObjectNotFoundException {
        when(giftCertificateDao.findByName(testName)).thenReturn(Optional.of(testGCerts.get(0)));
        when(giftCertificateDao.findByName(testNameIncorrect)).thenReturn(Optional.empty());

        var gCert = giftCertificateService.getByName(testName);

        assertEquals(testGCerts.get(0), gCert.get());

        assertThrows(ObjectNotFoundException.class, ()->giftCertificateService.getByName(testNameIncorrect));

    }
    @Test
    void updateTest(){
        var testPojo = getGCertSavePojo();
        when(giftCertificateMapper.createUpdatedGCertBySaveRequestPojoAndGCert(testPojo, testGCerts.get(0)))
                .thenReturn(testGCerts.get(0));
        when(giftCertificateDao.findById(testId)).thenReturn(Optional.ofNullable(testGCerts.get(0)));
        when(tagService.getTagsByTagName(testPojo.getTags())).thenReturn(getTestTags());

        assertDoesNotThrow(()->giftCertificateService.update(testPojo));
    }


    public static List<GiftCertificate> getGiftCertificates(){
        var tags = getTestTags();
        return List.of(
                new GiftCertificate(1L, "name1", "description1",
                        BigDecimal.valueOf(100), 5, LocalDateTime.now(), LocalDateTime.now(),
                        tags),
                new GiftCertificate(2L, "name2", "description2",
                        BigDecimal.valueOf(100), 5, LocalDateTime.now(), LocalDateTime.now(),
                        List.of(tags.get(0), tags.get(1))),
                new GiftCertificate(3L, "name3", "description3",
                        BigDecimal.valueOf(100), 5, LocalDateTime.now(), LocalDateTime.now(),
                        List.of(tags.get(2), tags.get(1)))
        );
    }

    private GiftCertificateSaveRequestPojo getGCertSavePojo(){
        return new GiftCertificateSaveRequestPojo(1L, "name1", "description1",
                BigDecimal.valueOf(100), 500,
                getTestTags()
                    .stream()
                    .map(Tag::getName)
                    .toList()
        );
    }
}