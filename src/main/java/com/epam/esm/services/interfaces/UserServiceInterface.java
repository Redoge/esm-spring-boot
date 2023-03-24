package com.epam.esm.services.interfaces;

import com.epam.esm.entities.User;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.UserSaveRequestPojo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    List<User> getAll();
    Optional<User> getById(long id) throws ObjectNotFoundException;
    User save(User user) throws ObjectIsExistException;
    void deleteById(@PathVariable long id) throws ObjectNotFoundException;
    User save(UserSaveRequestPojo userPojo) throws BadRequestException, ObjectIsExistException;
}
