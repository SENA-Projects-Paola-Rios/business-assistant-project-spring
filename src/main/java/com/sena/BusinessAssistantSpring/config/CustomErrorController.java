package com.sena.BusinessAssistantSpring.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> handleError(WebRequest webRequest) {
        Map<String, Object> errorDetails = new LinkedHashMap<>();
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", attributes.get("status"));
        errorDetails.put("error", attributes.get("error"));
        errorDetails.put("message", attributes.get("message"));
        errorDetails.put("path", attributes.get("path"));

        HttpStatus status = HttpStatus.valueOf((Integer) attributes.get("status"));
        return new ResponseEntity<>(errorDetails, status);
    }
}
