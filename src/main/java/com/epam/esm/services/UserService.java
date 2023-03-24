package com.epam.esm.services;

import com.epam.esm.entities.User;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
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
    public Optional<User> getById(long id) throws ObjectNotFoundException {
        var user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ObjectNotFoundException("User", id);
        return user;
    }

    @Override
    public User save(User user) throws ObjectIsExistException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ObjectIsExistException("User", user.getUsername());
        }
        return userRepository.save(user);//TODO: validate user
    }

    @Override
    public void deleteById(long id) throws ObjectNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ObjectNotFoundException("User", id);
        }
        userRepository.findById(id);
    }
}
