package com.epam.esm.services.interfaces;

import com.epam.esm.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    List<User> getAll();
    Optional<User> getById(long id);
    User save(User user);
    void deleteById(@PathVariable long id);
}
