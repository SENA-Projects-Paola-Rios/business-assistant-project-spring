package com.sena.BusinessAssistantSpring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    //ruta para cargar la pagina de inicio del sistema
    public String index() {
        return "index"; // Va a /WEB-INF/views/index.jsp
    }
    
}