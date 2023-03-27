package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.util.mappers.hateoas.models.GiftCertificateRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    public CollectionModel<GiftCertificateRepresentationModel> getCollectionModel(List<GiftCertificate> gCerts) throws Exception {
        List<GiftCertificateRepresentationModel> gCertsResources = new ArrayList<>();
        for(var gCert : gCerts) {
            gCertsResources.add(getRepresentationModel(gCert));
        }
        CollectionModel<GiftCertificateRepresentationModel> resources = CollectionModel.of(gCertsResources);
        resources.add(linkTo(methodOn(GiftCertificateController.class).getAll(null)).withSelfRel());
        resources.add(linkTo(methodOn(GiftCertificateController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return resources;
    }
}
