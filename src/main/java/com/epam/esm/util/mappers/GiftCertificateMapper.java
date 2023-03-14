package com.epam.esm.util.mappers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GiftCertificateMapper {
    public GiftCertificate createUpdatedGCertBySaveRequestPojoAndGCert(GiftCertificateSaveRequestPojo giftCertificatePojo,
                                                            GiftCertificate oldGiftCertificate) {
        return updateOnlyNewFieldsGiftCertificate(oldGiftCertificate, giftCertificatePojo);
    }

    private GiftCertificate updateOnlyNewFieldsGiftCertificate(GiftCertificate oldGCert, GiftCertificateSaveRequestPojo newGCert){
        boolean diffName = newGCert.getName() != null && !newGCert.getName().equals(oldGCert.getName());
        boolean diffDescription = newGCert.getDescription() != null && !newGCert.getDescription().equals(oldGCert.getDescription());
        boolean diffPrice = newGCert.getPrice() != null && !newGCert.getPrice().equals(oldGCert.getPrice());
        boolean diffDuration = newGCert.getDuration() != 0 && (newGCert.getDuration() != oldGCert.getDuration());
        boolean needToUpdate = (diffName || diffDescription || diffPrice || diffDuration);
        if(needToUpdate) {
            return new GiftCertificate(newGCert.getId(),
                    diffName ? newGCert.getName() : oldGCert.getName(),
                    diffDescription ? newGCert.getDescription() : oldGCert.getDescription(),
                    diffPrice ? newGCert.getPrice() : oldGCert.getPrice(),
                    diffDuration ? newGCert.getDuration() : oldGCert.getDuration(),
                    oldGCert.getCreateDate(),
                    LocalDateTime.now());
        }
        return oldGCert;
    }

    public GiftCertificate createGCertBySaveRequestPojoAndGCert(GiftCertificateSaveRequestPojo giftCertificatePojo) {
        var giftCetificate = new GiftCertificate();
        giftCetificate.setName(giftCertificatePojo.getName());
        giftCetificate.setDescription(giftCertificatePojo.getDescription());
        giftCetificate.setPrice(giftCertificatePojo.getPrice());
        giftCetificate.setDuration(giftCertificatePojo.getDuration());
        return giftCetificate;
    }
}
