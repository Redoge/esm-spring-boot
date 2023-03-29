package com.epam.esm.services.interfaces;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateServiceInterface {
    List<GiftCertificate> getAll();
    Page<GiftCertificate> getAll(Pageable pageable);

    Optional<GiftCertificate> getById(long id) throws ObjectNotFoundException;

    Optional<GiftCertificate> getByName(String name) throws ObjectNotFoundException;

    void deleteById(long id) throws ObjectNotFoundException;

    GiftCertificate save(GiftCertificate giftCertificate) throws BadRequestException, ObjectIsExistException;
    GiftCertificate save(GiftCertificateSaveRequestPojo giftCertificatePojo) throws  BadRequestException, ObjectIsExistException;

    void update(GiftCertificateSaveRequestPojo giftCertificatePojo) throws ObjectNotFoundException;

    Page<GiftCertificate> getByGiftCertificateSearchRequestPojo(GiftCertificateSearchRequestPojo certsSearchReqPojo, Pageable pageable);

    Page<GiftCertificate> getByUserId(Long id, Pageable pageable) throws ObjectNotFoundException;

}
