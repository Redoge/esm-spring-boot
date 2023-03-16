package com.epam.esm.controllers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.GiftCertificateIsExistException;
import com.epam.esm.exceptions.GiftCertificateNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;
import com.epam.esm.services.interfaces.GiftCertificateServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private final GiftCertificateServiceInterface giftCertificateService;
    public GiftCertificateController(GiftCertificateServiceInterface gCertService) {
        this.giftCertificateService = gCertService;
    }
    @GetMapping
    public ResponseEntity<List<GiftCertificate>> getGiftCertificates(@ModelAttribute GiftCertificateSearchRequestPojo req) {
        return ResponseEntity.ok(giftCertificateService.getByGiftCertificateSearchRequestPojo(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGiftCertificatesById(@PathVariable long id) throws GiftCertificateNotFoundException {
        var cert = giftCertificateService.getById(id);
        return ResponseEntity.ok(cert);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeGiftCertificateById(@PathVariable long id) throws GiftCertificateNotFoundException {
        giftCertificateService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody GiftCertificateSaveRequestPojo giftCertPojo) throws GiftCertificateIsExistException, BadRequestException {
        var giftCertificate  = giftCertificateService.save(giftCertPojo);
        return ResponseEntity.ok(giftCertificate);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody GiftCertificateSaveRequestPojo giftCert) throws GiftCertificateNotFoundException, BadRequestException {
        if(!(giftCert != null && (giftCert.getId() == 0 || id == giftCert.getId()))){
            throw new BadRequestException();
        }
        if (giftCert.getId() == 0) giftCert.setId(id);
        giftCertificateService.update(giftCert);
        var giftCertificate = giftCertificateService.getById(id);
        return ResponseEntity.ok(giftCertificate);
    }
}
