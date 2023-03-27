package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.UserController;

import com.epam.esm.entities.User;
import com.epam.esm.util.mappers.hateoas.models.UserRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoasMapper implements HateoasMapperInterface<UserRepresentationModel, User> {

    public UserRepresentationModel getRepresentationModel(User user){
        return new UserRepresentationModel(user);
    }

    public CollectionModel<UserRepresentationModel> getCollectionModel(List<User> users) throws Exception {
        List<UserRepresentationModel> tagResources = new ArrayList<>();
        for (var user : users) {
            tagResources.add(getRepresentationModel(user));
        }
        CollectionModel<UserRepresentationModel> resources = CollectionModel.of(tagResources);
        resources.add(linkTo(methodOn(UserController.class).getAll()).withSelfRel());
        resources.add(linkTo(methodOn(UserController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return resources;
    }
}