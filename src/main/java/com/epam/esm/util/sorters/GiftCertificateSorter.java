package com.epam.esm.util.sorter;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;
import com.epam.esm.util.enums.SortingOrder;
import com.epam.esm.util.enums.SortingType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class GiftCertificateSorter {
    public List<GiftCertificate> sortMainDtoByName(List<GiftCertificate> giftCertsMainDtoList, SortingOrder notation){
        if (notation.equals(SortingOrder.ASC)) {
            giftCertsMainDtoList.sort(Comparator.comparing(GiftCertificate::getName));
        } else if (notation.equals(SortingOrder.DESC)) {
            giftCertsMainDtoList.sort(Comparator.comparing(GiftCertificate::getName).reversed());
        }
        return giftCertsMainDtoList;
    }
    public List<GiftCertificate> sortMainDtoByTime(List<GiftCertificate> giftCertsMainDtoList, SortingOrder notation){
        if (notation.equals(SortingOrder.ASC)) {
            giftCertsMainDtoList.sort(Comparator.comparing(GiftCertificate::getLastUpdateDate));
        } else if (notation.equals(SortingOrder.DESC)) {
            giftCertsMainDtoList.sort(Comparator.comparing(GiftCertificate::getLastUpdateDate).reversed());
        }
        return giftCertsMainDtoList;
    }
    public List<GiftCertificate> sortedGiftCertificateMainDtoBySearchReq(List<GiftCertificate> gCerts, GiftCertificateSearchRequestPojo certsSearchReqPojo) {
        gCerts = new ArrayList<>(gCerts);
        if (certsSearchReqPojo.getSortingType() == SortingType.NAME) {
            gCerts = sortMainDtoByName(gCerts, certsSearchReqPojo.getSortingOrder());
        }else if (certsSearchReqPojo.getSortingType() == SortingType.TIME) {
            gCerts = sortMainDtoByTime(gCerts, certsSearchReqPojo.getSortingOrder());
        }
        return gCerts;
    }
}