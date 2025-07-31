package com.sena.BusinessAssistantSpring.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sena.BusinessAssistantSpring.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/*
	 * se implementa este servicio para manejar la informacion del usuario bajo el estandar
	 * de springframework, usualmente utilizado en el proceso de creacion de usuario
	 * y autenticacion
	 * */
	private UserRepository userRepository;
	
	 public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	 
	 
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	Optional<com.sena.BusinessAssistantSpring.model.User> optionalUser = userRepository.findByEmail(email);

    	if (!optionalUser.isPresent()) {
    	    return null;
    	}
        com.sena.BusinessAssistantSpring.model.User user = optionalUser.get();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
