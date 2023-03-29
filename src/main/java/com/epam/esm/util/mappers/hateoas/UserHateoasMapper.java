package com.epam.esm.util.mappers.hateoas;


import com.epam.esm.controllers.UserController;
import com.epam.esm.entities.User;
import com.epam.esm.util.mappers.hateoas.models.UserRepresentationModel;
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
public class UserHateoasMapper implements HateoasMapperInterface<UserRepresentationModel, User> {

    @Override
    public UserRepresentationModel getRepresentationModel(User user){
        return new UserRepresentationModel(user);
    }


    @Override
    public PagedModel<UserRepresentationModel> getPagedModel(Page<User> page, Pageable pageable) throws Exception {
        List<UserRepresentationModel> tagResources = page.getContent().stream().map(this::getRepresentationModel).toList();
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(),
                page.getTotalElements(), page.getTotalPages());
        PagedModel<UserRepresentationModel> resources = PagedModel.of(tagResources, metadata);
        resources.add(linkTo(methodOn(UserController.class).getAll(pageable)).withSelfRel());
        resources.add(linkTo(methodOn(UserController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return resources;
    }
}