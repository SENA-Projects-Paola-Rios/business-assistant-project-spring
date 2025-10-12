package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.model.User;
import com.sena.BusinessAssistantSpring.repository.UserRepository;
import com.sena.BusinessAssistantSpring.service.UserDetailsServiceImpl;
import com.sena.BusinessAssistantSpring.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
//@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    
    public AuthController(AuthenticationManager authenticationManager,
            UserRepository userRepository,
            UserDetailsServiceImpl userDetailsService,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
    	System.out.println("registrando");
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        	return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "The email is already registered."));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Intentar autenticar con email y contraseña
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            // Cargar detalles del usuario
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);

            // Retornar token si todo sale bien
            return ResponseEntity.ok(Map.of("token", jwt));

        } catch (BadCredentialsException e) {
            // Si las credenciales son incorrectas, retornar 401
            return ResponseEntity
                    .status(401)
                    .body(Map.of("error", "Invalid email or password"));
        } catch (Exception e) {
            // Capturar cualquier otro error inesperado
            e.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body(Map.of("error", "Unexpected error", "details", e.getMessage()));
        }
    }

}
