package com.epam.esm.util.mappers.hateoas.models;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.entities.GiftCertificate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(value = "GiftCertificateRepresentationModel", collectionRelation = "gift_certificates")
public class GiftCertificateRepresentationModel extends RepresentationModel<GiftCertificateRepresentationModel> {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private int duration;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdateDate;
    private List<TagRepresentationModel> tags;

    public GiftCertificateRepresentationModel(GiftCertificate giftCertificate){
        this.id = giftCertificate.getId();
        this.name = giftCertificate.getName();
        this.description = giftCertificate.getDescription();
        this.price = giftCertificate.getPrice();
        this.duration = giftCertificate.getDuration();
        this.createDate = giftCertificate.getCreateDate();
        this.lastUpdateDate = giftCertificate.getLastUpdateDate();
        this.tags = giftCertificate.getTags().stream().map(TagRepresentationModel::new).toList();
        try {
            add(linkTo(methodOn(GiftCertificateController.class).getById(giftCertificate.getId())).withSelfRel());
            add(linkTo(methodOn(GiftCertificateController.class).removeById(giftCertificate.getId())).withRel("delete").withType("DELETE"));
        }catch (Exception ignored) {}
    }
}
