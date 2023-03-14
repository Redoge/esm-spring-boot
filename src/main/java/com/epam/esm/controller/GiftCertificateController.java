package com.epam.esm.controller;

import com.epam.esm.service.interfaces.GiftCertificateServiceInterface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private final GiftCertificateServiceInterface gCertService;

    public GiftCertificateController(GiftCertificateServiceInterface gCertService) {
        this.gCertService = gCertService;
    }

}
