package com.epam.esm.util.mappers;

import com.epam.esm.entities.Order;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.OrderSaveRequestPojo;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;

    public OrderMapper(UserService userService, GiftCertificateService giftCertificateService) {
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
    }

    public Order mapPojoToOrder(OrderSaveRequestPojo orderPojo) throws ObjectNotFoundException {
        var order = new Order();
        var owner = userService.getById(orderPojo.getUserId()).get();
        var gCert = giftCertificateService.getById(orderPojo.getCertId()).get();
        order.setOwner(owner);
        order.setGiftCertificate(gCert);
        order.setPrice(gCert.getPrice());
        order.setPurchaseTime(LocalDateTime.now());
        return order;
    }
}
