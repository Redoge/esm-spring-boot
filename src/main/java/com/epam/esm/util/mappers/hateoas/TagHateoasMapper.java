package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.TagController;
import com.epam.esm.entities.Tag;
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
public class TagHateoasMapper implements HateoasMapperInterface<Tag> {
    @Override
    public EntityModel<Tag> getEntityModel(Tag tag) throws Exception {
        EntityModel<Tag> tagResource = EntityModel.of(tag);
        tagResource.add(linkTo(methodOn(TagController.class).getById(tag.getId())).withSelfRel());
        tagResource.add(linkTo(methodOn(TagController.class).removeById(tag.getId())).withRel("delete").withType(HttpMethod.DELETE.name()));
        return tagResource;
    }

    @Override
    public CollectionModel<EntityModel<Tag>> getCollectionModel(List<Tag> tags) throws Exception {
        List<EntityModel<Tag>> tagResources = new ArrayList<>();
        for (var tag : tags) {
            tagResources.add(getEntityModel(tag));
        }
        CollectionModel<EntityModel<Tag>> resources = CollectionModel.of(tagResources);
        resources.add(linkTo(methodOn(TagController.class).getAll()).withSelfRel());
        resources.add(linkTo(methodOn(TagController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return  resources;
    }
}
