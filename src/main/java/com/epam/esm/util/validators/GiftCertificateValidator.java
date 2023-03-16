package com.epam.esm.util.validators;

import com.epam.esm.entities.GiftCertificate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import static io.micrometer.common.util.StringUtils.isNotEmpty;
@Component
public class GiftCertificateValidator {
    public boolean isValid(GiftCertificate giftCertificate) {
        return isNotEmpty(giftCertificate.getName()) &&
                isNotEmpty(giftCertificate.getDescription()) &&
                giftCertificate.getDuration() > 0 &&
                giftCertificate.getPrice().intValue() > 0 &&
                !ObjectUtils.isEmpty(giftCertificate.getCreateDate()) &&
                !ObjectUtils.isEmpty(giftCertificate.getLastUpdateDate());
    }
}
