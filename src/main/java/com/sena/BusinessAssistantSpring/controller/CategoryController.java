package com.sena.BusinessAssistantSpring.controller;

import com.sena.BusinessAssistantSpring.model.Category;
import com.sena.BusinessAssistantSpring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Listar todas las categorías activas
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "category/category-list";
    }

    // Mostrar formulario de creación
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/category-form";
    }

    // Editar categoría
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "category/category-form";
        }
        return "redirect:/categories";
    }

    // Ver detalles
    @GetMapping("/view/{id}")
    public String showDetails(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "category/category-view";
        }
        return "redirect:/categories";
    }

    // Obtener como JSON
    @GetMapping("/json/{id}")
    @ResponseBody
    public ResponseEntity<Category> getCategoryAsJson(@PathVariable int id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Guardar nueva o actualizada
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    // Eliminar lógicamente
    @GetMapping("/delete/{id}")
    public String softDelete(@PathVariable int id) {
        categoryService.softDelete(id);
        return "redirect:/categories";
    }
}
