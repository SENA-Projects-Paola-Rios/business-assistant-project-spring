package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.model.User;
import com.sena.BusinessAssistantSpring.service.UserService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.io.IOException;
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
        return "user/user-list"; // /WEB-INF/views/user/user-list.jsp
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
        Optional<User> user = userService.getById(id);
        user.ifPresent(u -> model.addAttribute("user", u));
        return "user/user-view";
    }

    // Obtener usuario como JSON (AJAX)
    @GetMapping("/json/{id}")
    @ResponseBody
    public User getUserAsJson(@PathVariable int id) {
        return userService.getById(id).orElse(null);
    }

    // Eliminar usuario (soft delete)
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.softDelete(id);
        return "redirect:/users";
    }

    // Guardar usuario nuevo o editado con validación
    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") User user,
                           BindingResult result,
                           Model model) {
    	
    	System.out.println("ID: " + user.getId());
    	System.out.println("Name: '" + user.getName() + "'");
    	
    	System.out.println("Has errors: " + result.hasErrors());
    	result.getAllErrors().forEach(e -> System.out.println("Error: " + e.getDefaultMessage()));

        if (result.hasErrors()) {
            // Regresa al formulario si hay errores de validación
            return "user/user-form";
        }

       /* if (user.getId() <0 ) {
            // Crear nuevo usuario
            userService.create(user);
        } else {
            // Actualizar usuario existente
            userService.update(user);
        }*/

        return "redirect:/users";
    }
}
