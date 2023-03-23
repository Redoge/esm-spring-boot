package com.epam.esm.services;

import com.epam.esm.entities.User;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.services.interfaces.UserServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);//TODO: validate user
    }

    @Override
    public void deleteById(long id) {//TODO: check exist
        userRepository.findById(id);
    }
}
