package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateHateoasMapper implements HateoasMapperInterface<GiftCertificate> {
    @Override
    public EntityModel<GiftCertificate> getEntityModel(GiftCertificate gCert) throws Exception {
        EntityModel<GiftCertificate> gCertsResource = EntityModel.of(gCert);
        gCertsResource.add(linkTo(methodOn(GiftCertificateController.class).getById(gCert.getId())).withSelfRel());
        gCertsResource.add(linkTo(methodOn(GiftCertificateController.class).update(gCert.getId(), null)).withRel("update").withType(HttpMethod.PUT.name()));
        gCertsResource.add(linkTo(methodOn(GiftCertificateController.class).removeById(gCert.getId())).withRel("delete").withType(HttpMethod.DELETE.name()));
        return gCertsResource;
    }

    @Override
    public CollectionModel<EntityModel<GiftCertificate>> getCollectionModel(List<GiftCertificate> gCerts) throws Exception {
        List<EntityModel<GiftCertificate>> gCertsResources = new ArrayList<>();
        for(var gCert : gCerts) {
            gCertsResources.add(getEntityModel(gCert));
        }
        CollectionModel<EntityModel<GiftCertificate>> resources = CollectionModel.of(gCertsResources);
        resources.add(linkTo(methodOn(GiftCertificateController.class).getAll(null)).withSelfRel());
        resources.add(linkTo(methodOn(GiftCertificateController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return resources;
    }
}
