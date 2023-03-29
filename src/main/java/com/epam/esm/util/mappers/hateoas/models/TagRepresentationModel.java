package com.epam.esm.util.mappers.hateoas.models;

import com.epam.esm.controllers.TagController;
import com.epam.esm.entities.Tag;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Relation(value = "TagRepresentationModel", collectionRelation = "tags")
public class TagRepresentationModel extends RepresentationModel<TagRepresentationModel> {

    private final  Long id;
    private final String name;

    public TagRepresentationModel(Tag tag){
        this.id = tag.getId();
        this.name = tag.getName();

        try {
            add(linkTo(methodOn(TagController.class).getById(tag.getId())).withSelfRel());
            add(linkTo(methodOn(TagController.class).removeById(tag.getId())).withRel("delete"));
        } catch (Exception ignored) {

        }
    }

}