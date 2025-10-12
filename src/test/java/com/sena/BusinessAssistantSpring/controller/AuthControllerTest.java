package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.model.User;
import com.sena.BusinessAssistantSpring.repository.UserRepository;
import com.sena.BusinessAssistantSpring.service.UserDetailsServiceImpl;
import com.sena.BusinessAssistantSpring.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
	@Test
    void testRegister_UserAlreadyExists() {
        User user = new User("Juan", "juan@example.com", "123456", "USER");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = authController.register(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("already registered"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegister_Success() {
        User user = new User("Ana", "ana@example.com", "123456", "USER");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        ResponseEntity<?> response = authController.register(user);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("successfully"));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testLogin_Success() {
        User user = new User("Carlos", "carlos@example.com", "123456", "USER");
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetailsService.loadUserByUsername(user.getEmail())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("jwtToken");

        ResponseEntity<?> response = authController.login(user);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("jwtToken"));
    }

    @Test
    void testLogin_InvalidCredentials() {
        User user = new User("Luis", "luis@example.com", "wrongpassword", "USER");

        doThrow(new BadCredentialsException("Invalid credentials"))
        .when(authenticationManager)
        .authenticate(any(UsernamePasswordAuthenticationToken.class));



        ResponseEntity<?> response = authController.login(user);

        assertEquals(401, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Invalid email or password"));
    }
}
