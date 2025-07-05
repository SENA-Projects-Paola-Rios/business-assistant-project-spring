package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.model.User;
import com.sena.BusinessAssistantSpring.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        user.ifPresent(u -> model.addAttribute("user", u));
        return "user/user-form";
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

    // Guardar usuario nuevo o editado
    @PostMapping("/save")
    public String saveUser(@RequestParam(required = false) Integer id,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String role) {

        if (id == null) {
            // Crear
            User newUser = new User(name, email, password, role);
            userService.create(newUser);
        } else {
            // Actualizar
            User updatedUser = new User(id, name, email, password, role, null);
            userService.update(updatedUser);
        }

        return "redirect:/users";
    }
}
