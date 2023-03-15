package com.epam.esm.services.interfaces;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.GiftCertificateIsExistException;
import com.epam.esm.exceptions.GiftCertificateNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateServiceInterface {
    List<GiftCertificate> getAll();

    Optional<GiftCertificate> getById(long id) throws GiftCertificateNotFoundException;

    Optional<GiftCertificate> getByName(String name) throws GiftCertificateNotFoundException;

    void deleteById(long id) throws GiftCertificateNotFoundException;

    GiftCertificate save(GiftCertificate giftCertificate) throws GiftCertificateIsExistException, BadRequestException;
    GiftCertificate save(GiftCertificateSaveRequestPojo giftCertificatePojo) throws GiftCertificateIsExistException, BadRequestException;

    void update(GiftCertificateSaveRequestPojo giftCertificatePojo) throws GiftCertificateNotFoundException;

    List<GiftCertificate> getByGiftCertificateSearchRequestPojo(GiftCertificateSearchRequestPojo certsSearchReqPojo);
}
