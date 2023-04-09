package com.epam.esm.services;

import com.epam.esm.entities.User;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.UserSaveRequestPojo;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.services.interfaces.UserServiceInterface;
import com.epam.esm.util.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.StringConst.USER;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> getById(long id) throws ObjectNotFoundException {
        var user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ObjectNotFoundException(USER, id);
        return user;
    }

    @Override
    public User save(User user) throws ObjectIsExistException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ObjectIsExistException(USER, user.getUsername());
        }
        return userRepository.save(user);//TODO: validate user
    }

    @Override
    public void deleteById(long id) throws ObjectNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new ObjectNotFoundException(USER, id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User save(UserSaveRequestPojo userPojo) throws BadRequestException, ObjectIsExistException {
        var user = userMapper.mapUserPojoToUSer(userPojo);
        if(userRepository.existsByUsername(user.getUsername()))
            throw new ObjectIsExistException(USER, user.getUsername());
        return userRepository.save(user);
    }

    
}
