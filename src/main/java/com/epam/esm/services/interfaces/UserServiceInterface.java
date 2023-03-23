package com.epam.esm.services.interfaces;

import com.epam.esm.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    List<User> getAll();
    Optional<User> getById(long id);
    User save(User user);
}
