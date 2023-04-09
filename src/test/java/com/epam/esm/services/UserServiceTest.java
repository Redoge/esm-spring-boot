package com.epam.esm.services;


import com.epam.esm.entities.User;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectIsExistException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.UserSaveRequestPojo;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.util.enums.UserRole;
import com.epam.esm.util.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserSaveRequestPojo testUserSaveRequestPojo;

    @BeforeEach
    void setUp() {
        testUser = new User(
                1L,
                "username1",
                "password1",
                BigDecimal.valueOf(100),
                UserRole.USER,
                List.of()
        );
        testUserSaveRequestPojo = new UserSaveRequestPojo("John", "Doe");
    }

    @Test
    void testGetAll() {

        List<User> userList = new ArrayList<>();
        userList.add(testUser);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> actualUserList = userService.getAll();

        assertEquals(userList, actualUserList);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetAllWithPageable() {
        Pageable pageable = Pageable.unpaged();

        var userPage = new PageImpl<>(List.of(testUser), pageable, 1);

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        var actualUserPage = userService.getAll(pageable);

        assertEquals(userPage, actualUserPage);
    }

    @Test
    void testGetById() throws ObjectNotFoundException {
        // given
        long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.of(testUser));

        // when
        Optional<User> actualUser = userService.getById(id);

        // then
        assertEquals(Optional.of(testUser), actualUser);
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testGetByIdWithNonExistingUser() {
        // given
        long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ObjectNotFoundException.class, () -> userService.getById(id));
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void testSave() throws ObjectIsExistException {
        // given
        User newUser = testUser;

        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(userRepository.save(newUser)).thenReturn(newUser);

        // when
        User actualUser = userService.save(newUser);

        // then
        assertEquals(newUser, actualUser);
        verify(userRepository, times(1)).existsByUsername(newUser.getUsername());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testSaveWithExistingUsername() {
        // given
        User existingUser = testUser;

        when(userRepository.existsByUsername(existingUser.getUsername())).thenReturn(true);

        // when, then
        assertThrows(ObjectIsExistException.class, () -> userService.save(existingUser));

    }

    @Test
    void testDeleteById() throws ObjectNotFoundException {
        // given
        long id = 1L;

        when(userRepository.existsById(id)).thenReturn(true);

        // when
        userService.deleteById(id);

        // then
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteByIdWithNonExistingUser() {
        // given
        long id = 1L;

        when(userRepository.existsById(id)).thenReturn(false);

        // when, then
        assertThrows(ObjectNotFoundException.class, () -> userService.deleteById(id));
        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, never()).deleteById(id);
    }

    @Test
    void testSaveWithUserSaveRequestPojo() throws BadRequestException, ObjectIsExistException {
        // given
        User newUser = testUser;

        when(userMapper.mapUserPojoToUSer(testUserSaveRequestPojo)).thenReturn(newUser);
        when(userRepository.existsByUsername(newUser.getUsername())).thenReturn(false);
        when(userRepository.save(newUser)).thenReturn(newUser);

        // when
        User actualUser = userService.save(testUserSaveRequestPojo);

        // then
        assertEquals(newUser, actualUser);
        verify(userMapper, times(1)).mapUserPojoToUSer(testUserSaveRequestPojo);
        verify(userRepository, times(1)).existsByUsername(newUser.getUsername());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testSaveWithUserSaveRequestPojoWithExistingUsername() throws BadRequestException {
        // given
        User existingUser = testUser;

        when(userMapper.mapUserPojoToUSer(testUserSaveRequestPojo)).thenReturn(existingUser);
        when(userRepository.existsByUsername(existingUser.getUsername())).thenReturn(true);

        // when, then
        assertThrows(ObjectIsExistException.class, () -> userService.save(testUserSaveRequestPojo));
        verify(userMapper, times(1)).mapUserPojoToUSer(testUserSaveRequestPojo);
        verify(userRepository, times(1)).existsByUsername(existingUser.getUsername());
        verify(userRepository, never()).save(any());
    }
}