package com.epam.esm.services;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Order;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;
import com.epam.esm.repositories.GiftCertificateRepository;
import com.epam.esm.repositories.OrderRepository;
import com.epam.esm.repositories.UserRepository;
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
    private final OrderRepository orderRepository;
    private final TagService tagService;
    private final UserRepository userRepository;

    public GiftCertificateService(GiftCertificateRepository giftCertificateDao, GiftCertificateSorter giftCertificateSorter,
                                  GiftCertificateMapper giftCertificateMapper, GiftCertificateValidator giftCertificateValidator, OrderRepository orderRepository, TagService tagService, UserRepository userRepository) {
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateSorter = giftCertificateSorter;
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificateValidator = giftCertificateValidator;
        this.orderRepository = orderRepository;
        this.tagService = tagService;

        this.userRepository = userRepository;
    }

    public List<GiftCertificate> getAll() {
        return giftCertificateDao.findAll();
    }

    public Optional<GiftCertificate> getById(long id) throws ObjectNotFoundException {
        var gCerts = giftCertificateDao.findById(id);
        if (gCerts.isEmpty()) {
            throw new ObjectNotFoundException("Gift Certificate", id);
        }
        return gCerts;
    }

    public Optional<GiftCertificate> getByName(String name) throws ObjectNotFoundException {
        var gCerts = giftCertificateDao.findByName(name);
        if (gCerts.isEmpty()) {
            throw new ObjectNotFoundException("Gift Certificate", name);
        }
        return gCerts;
    }
    @Transactional
    public void deleteById(long id) throws ObjectNotFoundException {
        if (!giftCertificateDao.existsById(id)) {
            throw new ObjectNotFoundException("Gift Certificate", id);
        }
        giftCertificateDao.deleteById(id);
    }

    @Transactional
    public GiftCertificate save(GiftCertificate giftCertificate) throws BadRequestException, ObjectIsExistException {
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        var valid = giftCertificateValidator.isValid(giftCertificate);
        if (!valid) {
            throw new BadRequestException();
        }
        if (giftCertificateDao.existsByName(giftCertificate.getName())) {
            throw new ObjectIsExistException("Gift Certificate", giftCertificate.getName());
        }
        return giftCertificateDao.save(giftCertificate);
    }
    @Transactional
    public GiftCertificate save(GiftCertificateSaveRequestPojo giftCertificate) throws BadRequestException, ObjectIsExistException {
        var tags = tagService.getTagsByTagName(giftCertificate.getTags());
        var gCertBySaveRequestPojoAndGCert = giftCertificateMapper.createGCertBySaveRequestPojoAndGCert(giftCertificate, tags);
        return save(gCertBySaveRequestPojoAndGCert);
    }

    @Transactional
    public void update(GiftCertificateSaveRequestPojo giftCertificatePojo) throws ObjectNotFoundException {
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

    @Override
    public List<GiftCertificate> getByUserId(Long id) throws ObjectNotFoundException {
        if(!userRepository.existsById(id))
            throw new ObjectNotFoundException("User", id);

        return orderRepository.findAllByOwnerId(id)
                .stream()
                .map(Order::getGiftCertificate)
                .distinct()
                .toList();
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
