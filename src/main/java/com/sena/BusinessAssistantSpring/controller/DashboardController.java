package com.sena.BusinessAssistantSpring.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Validar si el usuario está autenticado
        Object loggedUser = session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login"; // Redirige si no hay sesión activa
        }

        // Puedes pasar datos al modelo si lo necesitas
        model.addAttribute("user", loggedUser);

        return "dashboard"; // Apunta a /WEB-INF/views/dashboard.jsp si usas JSP
    }
}
