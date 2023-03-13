package com.epam.esm.pojo;

import com.epam.esm.util.enums.SortingOrder;
import com.epam.esm.util.enums.SortingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateSearchRequestPojo {
    private String name;
    private String description;
    private String tagName;
    private SortingType sortingType;
    private SortingOrder sortingOrder = SortingOrder.ASC;
}
