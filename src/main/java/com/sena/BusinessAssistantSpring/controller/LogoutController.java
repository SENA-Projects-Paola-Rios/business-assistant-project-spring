package com.sena.BusinessAssistantSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {

	//este metodo invalida la sesion
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // No crear si no existe
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login"; // Redirige al controlador que sirve la vista login.jsp
    }
}
