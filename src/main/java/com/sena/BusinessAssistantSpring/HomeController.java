package com.sena.BusinessAssistantSpring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Va a /WEB-INF/views/index.jsp
    }
}