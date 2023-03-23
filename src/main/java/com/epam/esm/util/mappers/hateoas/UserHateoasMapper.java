package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.UserController;
import com.epam.esm.entities.User;
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
public class UserHateoasMapper implements HateoasMapperInterface<User> {
    @Override
    public EntityModel<User> getEntityModel(User user) throws Exception {
        EntityModel<User> tagResource = EntityModel.of(user);
        tagResource.add(linkTo(methodOn(UserController.class).getById(user.getId())).withSelfRel());
        tagResource.add(linkTo(methodOn(UserController.class).removeById(user.getId())).withRel("delete").withType(HttpMethod.DELETE.name()));
        return tagResource;
    }

    @Override
    public CollectionModel<EntityModel<User>> getCollectionModel(List<User> users) throws Exception {
        List<EntityModel<User>> tagResources = new ArrayList<>();
        for (var user : users) {
            tagResources.add(getEntityModel(user));
        }
        CollectionModel<EntityModel<User>> resources = CollectionModel.of(tagResources);
        resources.add(linkTo(methodOn(UserController.class).getAll()).withSelfRel());
        resources.add(linkTo(methodOn(UserController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return  resources;
    }    }

