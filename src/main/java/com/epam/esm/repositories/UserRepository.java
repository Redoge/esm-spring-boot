package com.epam.esm.repositories;

import com.epam.esm.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
