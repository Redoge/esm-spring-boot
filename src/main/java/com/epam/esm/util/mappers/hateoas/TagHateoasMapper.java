package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.TagController;
import com.epam.esm.entities.Tag;
import com.epam.esm.util.mappers.hateoas.models.TagRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagHateoasMapper implements HateoasMapperInterface<TagRepresentationModel, Tag> {
    @Override
    public TagRepresentationModel getRepresentationModel(Tag tag) throws Exception {
        return new TagRepresentationModel(tag);
    }

    @Override
    public CollectionModel<TagRepresentationModel> getCollectionModel(List<Tag> tags) throws Exception {
        List<TagRepresentationModel> tagResources = new ArrayList<>();
        for (var tag : tags) {
            tagResources.add(getRepresentationModel(tag));
        }
        CollectionModel<TagRepresentationModel> resources = CollectionModel.of(tagResources);
        resources.add(linkTo(methodOn(TagController.class).getAll()).withSelfRel());
        resources.add(linkTo(methodOn(TagController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return  resources;
    }
}
