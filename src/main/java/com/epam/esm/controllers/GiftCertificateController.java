package com.epam.esm.controllers;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.GiftCertificateSaveRequestPojo;
import com.epam.esm.pojo.GiftCertificateSearchRequestPojo;
import com.epam.esm.services.interfaces.GiftCertificateServiceInterface;
import com.epam.esm.util.mappers.hateoas.models.GiftCertificateRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.epam.esm.util.StringConst.deletedSuccessfully;
import static com.epam.esm.util.consts.Paths.CERTIFICATES_PATH;


@RestController
@RequestMapping(CERTIFICATES_PATH)
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateServiceInterface giftCertificateService;
    private final HateoasMapperInterface<GiftCertificateRepresentationModel, GiftCertificate> hateoasMapper;

    @GetMapping
    public PagedModel<GiftCertificateRepresentationModel> getAll(@ModelAttribute GiftCertificateSearchRequestPojo req, Pageable pageable) throws Exception {
        var gCerts = giftCertificateService.getByGiftCertificateSearchRequestPojo(req, pageable);
        return hateoasMapper.getPagedModel(gCerts, pageable);
    }

    @GetMapping("/{id}")
    public GiftCertificateRepresentationModel getById(@PathVariable long id) throws Exception {
        var gCert = giftCertificateService.getById(id).get();
        return hateoasMapper.getRepresentationModel(gCert);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable long id) throws ObjectNotFoundException {
        giftCertificateService.deleteById(id);
        return ResponseEntity.ok(deletedSuccessfully);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody GiftCertificateSaveRequestPojo giftCertPojo) throws BadRequestException, ObjectIsExistException {
        var giftCertificate  = giftCertificateService.save(giftCertPojo);
        return ResponseEntity.ok(giftCertificate);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody GiftCertificateSaveRequestPojo giftCert) throws BadRequestException, ObjectNotFoundException {
        if(!(giftCert != null && (giftCert.getId() == 0 || id == giftCert.getId()))){
            throw new BadRequestException();
        }
        if (giftCert.getId() == 0) giftCert.setId(id);
        giftCertificateService.update(giftCert);
        var giftCertificate = giftCertificateService.getById(id);
        return ResponseEntity.ok(giftCertificate);
    }
    @GetMapping("/name/{name}")
    public PagedModel<GiftCertificateRepresentationModel> getUserByPartName(@PathVariable String name, Pageable pageable) throws Exception {
        var gCerts = giftCertificateService.getByPartName(name, pageable);
        return hateoasMapper.getPagedModel(gCerts, pageable);
    }
}
