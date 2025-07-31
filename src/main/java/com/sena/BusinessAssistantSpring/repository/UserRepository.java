package com.sena.BusinessAssistantSpring.repository;

import com.sena.BusinessAssistantSpring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndPasswordAndDeletedAtIsNull(String email, String password);

    List<User> findAllByDeletedAtIsNull();

    Optional<User> findByIdAndDeletedAtIsNull(Integer id);
    
    Optional<User> findByEmail(String email); 
    
    boolean existsByEmail(String email);
    
}
