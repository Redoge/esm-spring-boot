package com.epam.esm.util.mappers.hateoas.models;

import com.epam.esm.controllers.UserController;
import com.epam.esm.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRepresentationModel extends RepresentationModel<UserRepresentationModel> {
    private long id;
    private final String username;
    private final BigDecimal money;

    public UserRepresentationModel(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.money = user.getMoney();

        try {
            add(linkTo(methodOn(UserController.class).getById(user.getId())).withSelfRel());
            add(linkTo(methodOn(UserController.class).getCertificatesByUserId(user.getId(), null)).withRel("gift_certificates"));
            add(linkTo(methodOn(UserController.class).getOrdersByUserId(user.getId())).withRel("orders"));
            add(linkTo(methodOn(UserController.class).removeById(user.getId())).withRel("delete").withType("DELETE"));
        } catch (Exception ignored) {
        }
    }
}