package com.epam.esm.controllers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.GiftCertificateIsExistException;
import com.epam.esm.exceptions.GiftCertificateNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;
import com.epam.esm.services.interfaces.GiftCertificateServiceInterface;
import jakarta.transaction.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {
    private final GiftCertificateServiceInterface giftCertificateService;
    public GiftCertificateController(GiftCertificateServiceInterface gCertService) {
        this.giftCertificateService = gCertService;
    }
    @GetMapping
    public CollectionModel<EntityModel<GiftCertificate>> getGiftCertificates(@ModelAttribute GiftCertificateSearchRequestPojo req) throws GiftCertificateNotFoundException, BadRequestException, GiftCertificateIsExistException {
        var gCerts = giftCertificateService.getByGiftCertificateSearchRequestPojo(req);
        List<EntityModel<GiftCertificate>> gCertsResources = new ArrayList<>();
        for(var gCert : gCerts) {
            EntityModel<GiftCertificate> gCertsResource = EntityModel.of(gCert);
            gCertsResource.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificatesById(gCert.getId())).withSelfRel());
            gCertsResource.add(linkTo(methodOn(GiftCertificateController.class).update(gCert.getId(), null)).withRel("update").withType(HttpMethod.PUT.name()));
            gCertsResource.add(linkTo(methodOn(GiftCertificateController.class).removeGiftCertificateById(gCert.getId())).withRel("delete").withType(HttpMethod.DELETE.name()));
            gCertsResources.add(gCertsResource);
        }
        CollectionModel<EntityModel<GiftCertificate>> resources = CollectionModel.of(gCertsResources);
        resources.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificates(null)).withSelfRel());
        resources.add(linkTo(methodOn(GiftCertificateController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return resources;
    }

    @GetMapping("/{id}")
    public EntityModel<GiftCertificate> getGiftCertificatesById(@PathVariable long id) throws GiftCertificateNotFoundException, BadRequestException {
        var gCert = giftCertificateService.getById(id).get();
        EntityModel<GiftCertificate> gCertsResource = EntityModel.of(gCert);
        gCertsResource.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificatesById(gCert.getId())).withSelfRel());
        gCertsResource.add(linkTo(methodOn(GiftCertificateController.class).update(gCert.getId(), null)).withRel("update").withType(HttpMethod.PUT.name()));
        gCertsResource.add(linkTo(methodOn(GiftCertificateController.class).removeGiftCertificateById(gCert.getId())).withRel("delete").withType(HttpMethod.DELETE.name()));
        return gCertsResource;
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
