package com.epam.esm.services.interfaces;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateServiceInterface {
    List<GiftCertificate> getAll();

    Optional<GiftCertificate> getById(long id) throws ObjectNotFoundException;

    Optional<GiftCertificate> getByName(String name) throws ObjectNotFoundException;

    void deleteById(long id) throws ObjectNotFoundException;

    GiftCertificate save(GiftCertificate giftCertificate) throws BadRequestException, ObjectIsExistException;
    GiftCertificate save(GiftCertificateSaveRequestPojo giftCertificatePojo) throws  BadRequestException, ObjectIsExistException;

    void update(GiftCertificateSaveRequestPojo giftCertificatePojo) throws ObjectNotFoundException;

    List<GiftCertificate> getByGiftCertificateSearchRequestPojo(GiftCertificateSearchRequestPojo certsSearchReqPojo);

    List<GiftCertificate> getByUserId(Long id);

}
