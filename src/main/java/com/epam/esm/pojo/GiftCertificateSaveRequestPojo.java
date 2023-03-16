package com.epam.esm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateSaveRequestPojo {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private List<String> tags;
}
