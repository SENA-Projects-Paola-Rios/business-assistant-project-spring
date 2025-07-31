package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.model.User;
import com.sena.BusinessAssistantSpring.service.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Mostrar lista de usuarios
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllActiveUsers();
        model.addAttribute("users", users);
        return "user/user-list";
    }

    // Mostrar formulario vacío para crear usuario
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user/user-form";
    }

    // Mostrar formulario de edición
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<User> user = userService.getById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/user-form";
        }
        return "redirect:/users";
    }

    // Ver detalles de usuario
    @GetMapping("/view/{id}")
    public String showDetails(@PathVariable int id, Model model) {
        userService.getById(id).ifPresent(u -> model.addAttribute("user", u));
        return "user/user-view";
    }

    // Obtener usuario como JSON (AJAX)
    @GetMapping("/json/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserAsJson(@PathVariable int id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar usuario (soft delete)
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.softDelete(id);
        return "redirect:/users";
    }

    

    
    @PostMapping("/save-ajax")
    @ResponseBody
    public ResponseEntity<?> saveCategoryAjax(@Valid @RequestBody User user, BindingResult result) {
    	
    	//verifico que no tenga id, lo que significa que se esta haciendo un create
    	if (user.getId() == null || user.getId() == 0) {
    		user.setId(null); // Forzar null para que JPA haga insert
        }

    	//manejo de errores cuando las validaciones del modelo no se cumplen
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        if(user.getId() == null) {
        	userService.save(user);
        } else {
        	userService.update(user);
        }
        
        
        //manejo de la respuesta cuando todo salio bien
        return ResponseEntity.ok("Category saved successfully");
    }
}
