package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.model.User;
import com.sena.BusinessAssistantSpring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRestControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRestController userRestController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345");
    }

    @Test
    void testGetAllUsers() {
        when(userService.getAllActiveUsers()).thenReturn(List.of(user));

        ResponseEntity<List<User>> response = userRestController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetUserById_Found() {
        when(userService.getById(1)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userRestController.getUserById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof User);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userService.getById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userRestController.getUserById(1);
        });

        assertTrue(exception.getMessage().contains("User with ID 1 not found"));
    }

    @Test
    void testCreateUser_Success() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        User newUser = new User();
        newUser.setName("Jane Doe");
        newUser.setEmail("jane@example.com");
        newUser.setPassword("12345");

        ResponseEntity<?> response = userRestController.createUser(newUser);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_MissingPassword() {
        User newUser = new User();
        newUser.setName("Jane Doe");
        newUser.setEmail("jane@example.com");

        ResponseEntity<?> response = userRestController.createUser(newUser);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Password must not be empty"));
    }

    @Test
    void testUpdateUser_Success() {
        when(userService.getById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        user.setPassword("newPassword");
        ResponseEntity<?> response = userRestController.updateUser(1, user);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).update(any(User.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userService.getById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userRestController.updateUser(1, user);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("User not found"));
    }

    @Test
    void testDeleteUser_Success() {
        when(userService.getById(1)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userRestController.deleteUser(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).softDelete(1);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userService.getById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userRestController.deleteUser(1);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("User not found"));
    }
}
