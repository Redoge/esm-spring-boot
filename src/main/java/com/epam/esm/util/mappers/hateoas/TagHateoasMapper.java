package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.TagController;
import com.epam.esm.entities.Tag;
import com.epam.esm.util.mappers.hateoas.models.TagRepresentationModel;
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
public class TagHateoasMapper implements HateoasMapperInterface<TagRepresentationModel, Tag> {
    @Override
    public TagRepresentationModel getRepresentationModel(Tag tag) {
        return new TagRepresentationModel(tag);
    }

    @Override
    public PagedModel<TagRepresentationModel> getPagedModel(Page<Tag> page, Pageable pageable) throws Exception {
        List<TagRepresentationModel> tagResources = page.getContent().stream().map(this::getRepresentationModel).toList();
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(),
                page.getTotalElements(), page.getTotalPages());
        PagedModel<TagRepresentationModel> resources = PagedModel.of(tagResources, metadata);
        resources.add(linkTo(methodOn(TagController.class).getAll(null)).withSelfRel());
        resources.add(linkTo(methodOn(TagController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return resources;
    }
}
