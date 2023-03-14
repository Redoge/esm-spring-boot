package com.epam.esm.service.interfaces;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateServiceInterface {
    List<GiftCertificate> getAll();

    Optional<GiftCertificate> getById(long id);

    Optional<GiftCertificate> getByName(String name);

    void deleteById(long id);

    void save(GiftCertificate giftCertificate);

    void update(GiftCertificateSaveRequestPojo giftCertificatePojo);

    List<GiftCertificate> getByGiftCertificateSearchRequestPojo(GiftCertificateSearchRequestPojo certsSearchReqPojo);
}
