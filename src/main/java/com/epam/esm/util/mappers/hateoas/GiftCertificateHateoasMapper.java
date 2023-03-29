package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.util.mappers.hateoas.models.GiftCertificateRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class GiftCertificateHateoasMapper implements HateoasMapperInterface<GiftCertificateRepresentationModel,GiftCertificate> {
    @Override
    public GiftCertificateRepresentationModel getRepresentationModel(GiftCertificate gCert) {
        return new GiftCertificateRepresentationModel(gCert);
    }

    @Override
    public PagedModel<GiftCertificateRepresentationModel> getPagedModel(Page<GiftCertificate> page, Pageable pageable) throws Exception {
        List<GiftCertificateRepresentationModel> tagResources = page.getContent().stream().map(this::getRepresentationModel).toList();
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(),
                page.getTotalElements(), page.getTotalPages());
        PagedModel<GiftCertificateRepresentationModel> resources = PagedModel.of(tagResources, metadata);
        resources.add(linkTo(methodOn(GiftCertificateController.class).getAll(null, pageable)).withSelfRel());
        resources.add(linkTo(methodOn(GiftCertificateController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return resources;
    }
}
