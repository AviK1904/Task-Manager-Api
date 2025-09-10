package com.aviralproject.TaskManager.Service;

import com.aviralproject.TaskManager.dto.AddUserRequestdto;
import com.aviralproject.TaskManager.dto.Userdto;
import com.aviralproject.TaskManager.entity.User;
import com.aviralproject.TaskManager.exception.UserNotFoundException;
import com.aviralproject.TaskManager.repository.UserRepository;
import com.aviralproject.TaskManager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();
        userService = new UserServiceImpl(userRepository,modelMapper);
    }

    @Test
    void testCreateUser() {
        AddUserRequestdto request = new AddUserRequestdto("Aviral", "Kumar", "Asia/Kolkata", true);
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setFirstName("Aviral");
        savedUser.setLastName("Kumar");
        savedUser.setTimezone("Asia/Kolkata");
        savedUser.setActive(true);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Userdto result = userService.createUser(request);

        assertNotNull(result);
        assertEquals("Aviral", result.getFirstName());
        assertEquals("Kumar", result.getLastName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById_Found() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Aviral");
        user.setLastName("Kumar");
        user.setTimezone("Asia/Kolkata");
        user.setActive(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Userdto result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Aviral", result.getFirstName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        // Since the method is void, you can use doNothing() to be explicit
        // about mocking the behavior, although it's often not required.
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userService.deleteUser(userId);

        // Verify
        // Check that deleteById was called exactly once with the correct ID.
        verify(userRepository, times(1)).deleteById(userId);
    }
}
