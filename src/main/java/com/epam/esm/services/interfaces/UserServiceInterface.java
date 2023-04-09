package com.epam.esm.services.interfaces;

import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.User;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.UserSaveRequestPojo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    List<User> getAll();
    Page<User> getAll(Pageable pageable);
    Optional<User> getById(long id) throws ObjectNotFoundException;
    User save(User user) throws ObjectIsExistException;
    void deleteById(@PathVariable long id) throws ObjectNotFoundException;
    User save(UserSaveRequestPojo userPojo) throws BadRequestException, ObjectIsExistException;
    Page<User> getByPartName(String partName, Pageable pageable);

}
