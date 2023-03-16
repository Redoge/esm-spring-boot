package com.epam.esm.services;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.GiftCertificateIsExistException;
import com.epam.esm.exceptions.GiftCertificateNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;
import com.epam.esm.repositories.GiftCertificateRepository;
import com.epam.esm.services.interfaces.GiftCertificateServiceInterface;
import com.epam.esm.util.mappers.GiftCertificateMapper;
import com.epam.esm.util.sorters.GiftCertificateSorter;
import com.epam.esm.util.validators.GiftCertificateValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

@Service
public class GiftCertificateService implements GiftCertificateServiceInterface {
    private final GiftCertificateRepository giftCertificateDao;
    private final GiftCertificateSorter giftCertificateSorter;
    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificateValidator giftCertificateValidator;
    private final TagService tagService;

    public GiftCertificateService(GiftCertificateRepository giftCertificateDao, GiftCertificateSorter giftCertificateSorter,
                                  GiftCertificateMapper giftCertificateMapper, GiftCertificateValidator giftCertificateValidator, TagService tagService) {
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateSorter = giftCertificateSorter;
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificateValidator = giftCertificateValidator;
        this.tagService = tagService;

    }

    public List<GiftCertificate> getAll() {
        return giftCertificateDao.findAll();
    }

    public Optional<GiftCertificate> getById(long id) throws GiftCertificateNotFoundException {
        var gCerts = giftCertificateDao.findById(id);
        if (gCerts.isEmpty()) {
            throw new GiftCertificateNotFoundException("id " + id);
        }
        return gCerts;
    }

    public Optional<GiftCertificate> getByName(String name) throws GiftCertificateNotFoundException {
        var gCerts = giftCertificateDao.findByName(name);
        if (gCerts.isEmpty()) {
            throw new GiftCertificateNotFoundException("name " + name);
        }
        return gCerts;
    }
    @Transactional
    public void deleteById(long id) throws GiftCertificateNotFoundException {
        if (!giftCertificateDao.existsById(id)) {
            throw new GiftCertificateNotFoundException("id " + id);
        }
        giftCertificateDao.deleteById(id);
    }

    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) throws GiftCertificateIsExistException, BadRequestException {
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        var valid = giftCertificateValidator.isValid(giftCertificate);
        if (!valid) {
            throw new BadRequestException();
        }
        if (giftCertificateDao.existsByName(giftCertificate.getName())) {
            throw new GiftCertificateIsExistException("name " + giftCertificate.getName());
        }
        return giftCertificateDao.save(giftCertificate);
    }
    @Transactional
    public GiftCertificate save(GiftCertificateSaveRequestPojo giftCertificate) throws GiftCertificateIsExistException, BadRequestException {
        var tags = tagService.getTagsByTagName(giftCertificate.getTags());
        var gCertBySaveRequestPojoAndGCert = giftCertificateMapper.createGCertBySaveRequestPojoAndGCert(giftCertificate, tags);
        return save(gCertBySaveRequestPojoAndGCert);
    }

    @Transactional
    public void update(GiftCertificateSaveRequestPojo giftCertificatePojo) throws GiftCertificateNotFoundException {
        Optional<GiftCertificate> gCert = getById(giftCertificatePojo.getId());
        if (gCert.isPresent()) {
            var newGCert = giftCertificateMapper.createUpdatedGCertBySaveRequestPojoAndGCert(giftCertificatePojo, gCert.get());
            var tagsName = giftCertificatePojo.getTags();
            if(tagsName != null) {
                List<Tag> tags = tagService.getTagsByTagName(tagsName);
                newGCert.setTags(tags);
            }
            giftCertificateDao.save(newGCert);
        }
    }

    public List<GiftCertificate> getByGiftCertificateSearchRequestPojo(GiftCertificateSearchRequestPojo certsSearchReqPojo) {
        List<GiftCertificate> gCerts = getGiftCertificateMainDtoBySearchReq(certsSearchReqPojo);
        return giftCertificateSorter.sortedGiftCertificateMainDtoBySearchReq(gCerts, certsSearchReqPojo);
    }

    private List<GiftCertificate> getGiftCertificateMainDtoBySearchReq(GiftCertificateSearchRequestPojo certsSearchReqPojo) {
        List<GiftCertificate> gCerts;
        var nameIsPresent = isNotEmpty(certsSearchReqPojo.getName());
        var descriptionIsPresent = isNotEmpty(certsSearchReqPojo.getDescription());
        var tagIsPresent = isNotEmpty(certsSearchReqPojo.getTagName());
        if (nameIsPresent) {
            gCerts = tagIsPresent ?
                    giftCertificateDao.findByNameContainingAndTagsName(certsSearchReqPojo.getName(), certsSearchReqPojo.getTagName()) :
                    giftCertificateDao.findByNameContaining(certsSearchReqPojo.getName());
        } else if (descriptionIsPresent) {
            gCerts = tagIsPresent ?
                    giftCertificateDao.findByDescriptionContainingAndTagsName(certsSearchReqPojo.getDescription(), certsSearchReqPojo.getTagName()) :
                    giftCertificateDao.findByDescriptionContaining(certsSearchReqPojo.getDescription());
        } else {
            gCerts = tagIsPresent ?
                    giftCertificateDao.findByTagsName(certsSearchReqPojo.getTagName()) :
                    getAll();
        }
        return gCerts;
    }

}
