package com.epam.esm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveRequestPojo {
    private Long certId;
    private Long userId;
}
