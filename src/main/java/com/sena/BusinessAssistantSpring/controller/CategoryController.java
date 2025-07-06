package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.model.Category;
import com.sena.BusinessAssistantSpring.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/category-list";
    }

    @GetMapping("/json/{id}")
    @ResponseBody
    public ResponseEntity<Category> getCategoryAsJson(@PathVariable int id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save-ajax")
    @ResponseBody
    public ResponseEntity<?> saveCategoryAjax(@Valid @RequestBody Category category, BindingResult result) {
    	
    	//verifico que no tenga id, lo que significa que se esta haciendo un create
    	if (category.getId() == null || category.getId() == 0) {
            category.setId(null); // Forzar null para que JPA haga insert
        }

    	//manejo de errores cuando las validaciones del modelo no se cumplen
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        categoryService.save(category);
        
        //manejo de la respuesta cuando todo salio bien
        return ResponseEntity.ok("Category saved successfully");
    }

    @GetMapping("/delete/{id}")
    public String softDelete(@PathVariable int id) {
        categoryService.softDelete(id);
        return "redirect:/categories";
    }
}
