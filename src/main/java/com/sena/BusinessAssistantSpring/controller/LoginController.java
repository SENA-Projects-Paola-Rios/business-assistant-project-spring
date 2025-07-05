package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.model.User;
import com.sena.BusinessAssistantSpring.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    //carga la pagina de autenticacion en el sistema por metodo get
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Carga /WEB-INF/views/login.jsp
    }

    //procesa el formulario de autenticacion por metodo post
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

    	//obtiene el usuario que corresponda al email y password y adicional valida el soft delete
        User user = userRepository.findByEmailAndPasswordAndDeletedAtIsNull(email, password);

        if (user != null) {
            session.setAttribute("loggedUser", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials.");
            return "login";
        }
    }

}
