package com.epam.esm.services.interfaces;

import com.epam.esm.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    List<User> getUsers();
    Optional<User> getById();
    User save(User user);
}
