package com.epam.esm.controllers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.GiftCertificateIsExistException;
import com.epam.esm.exceptions.GiftCertificateNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;
import com.epam.esm.services.interfaces.GiftCertificateServiceInterface;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private final GiftCertificateServiceInterface giftCertificateService;
    private final HateoasMapperInterface<GiftCertificate> hateoasMapper;
    public GiftCertificateController(GiftCertificateServiceInterface gCertService, HateoasMapperInterface<GiftCertificate> hateoasMapper) {
        this.giftCertificateService = gCertService;
        this.hateoasMapper = hateoasMapper;
    }
    @GetMapping
    public CollectionModel<EntityModel<GiftCertificate>> getAll(@ModelAttribute GiftCertificateSearchRequestPojo req) throws Exception {
        var gCerts = giftCertificateService.getByGiftCertificateSearchRequestPojo(req);
        return hateoasMapper.getCollectionModel(gCerts);
    }

    @GetMapping("/{id}")
    public EntityModel<GiftCertificate> getById(@PathVariable long id) throws Exception {
        var gCert = giftCertificateService.getById(id).get();
        return hateoasMapper.getEntityModel(gCert);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable long id) throws GiftCertificateNotFoundException {
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
