package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.interfaces.GiftCertificateServiceInterface;
import com.epam.esm.util.mapper.GiftCertificateMapper;
import com.epam.esm.util.sorter.GiftCertificateSorter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isNotEmpty;
import static java.lang.String.valueOf;

@Service
public class GiftCertificateService implements GiftCertificateServiceInterface {
    private final GiftCertificateRepository giftCertificateDao;
    private final GiftCertificateSorter giftCertificateSorter;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagService tagService;

    public GiftCertificateService(GiftCertificateRepository giftCertificateDao, GiftCertificateSorter giftCertificateSorter,
                                  GiftCertificateMapper giftCertificateMapper, TagService tagService) {
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateSorter = giftCertificateSorter;
        this.giftCertificateMapper = giftCertificateMapper;
        this.tagService = tagService;

    }

    public List<GiftCertificate> getAll() {
        return giftCertificateDao.findAll();
    }

    public Optional<GiftCertificate> getById(long id) throws GiftCertificateNotFoundException {
        if(!giftCertificateDao.existsById(id)){
            throw new GiftCertificateNotFoundException("id " + id);
        }
        return giftCertificateDao.findById(id);
    }

    public Optional<GiftCertificate> getByName(String name) throws GiftCertificateNotFoundException {
        if(!giftCertificateDao.existsByName(name)){
            throw new GiftCertificateNotFoundException("name " + name);
        }
        return giftCertificateDao.findByName(name);
    }

    public void deleteById(long id) throws GiftCertificateNotFoundException {
        if(!giftCertificateDao.existsById(id)){
            throw new GiftCertificateNotFoundException("id " + id);
        }
        giftCertificateDao.deleteById(id);
    }

    public void save(GiftCertificate giftCertificate) {
        giftCertificateDao.save(giftCertificate);
    }

    @Transactional
    public void update(GiftCertificateSaveRequestPojo giftCertificatePojo) {
        Optional<GiftCertificate> gCert = getById(giftCertificatePojo.getId());
        if(gCert.isPresent()) {
            var newGCert = giftCertificateMapper.createUpdatedGCertBySaveRequestPojoAndGCert(giftCertificatePojo, gCert.get());
            List<Tag> tags = tagService.saveAll(giftCertificatePojo.getTags()
                    .stream()
                    .map(Tag::new)
                    .toList());
            newGCert.setTags(tags);
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
