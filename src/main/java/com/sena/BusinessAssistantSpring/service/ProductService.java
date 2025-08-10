package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.dto.CategoryDTO;
import com.sena.BusinessAssistantSpring.dto.ProductDTO;
import com.sena.BusinessAssistantSpring.exception.BusinessValidationException;
import com.sena.BusinessAssistantSpring.model.Category;
import com.sena.BusinessAssistantSpring.model.Product;
import com.sena.BusinessAssistantSpring.model.ProductCategory;
import com.sena.BusinessAssistantSpring.repository.ProductCategoryRepository;
import com.sena.BusinessAssistantSpring.repository.ProductRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    // Devuelve el listado de productos que no han sido eliminados (deletedAt es null)
    public List<Product> findAll() {
        return productRepository.findByDeletedAtIsNull();
    }
    
    // Devuelve el listado de productos con la realcion de categorias que no han sido eliminados (deletedAt es null)
    public List<ProductDTO> findAllActiveWithCategories() {
        // Traer productos activos
        List<Product> products = productRepository.findByDeletedAtIsNull();

        // Por cada producto, buscar categorías activas asociadas
        List<ProductDTO> dtos = new ArrayList<>();

        for (Product p : products) {
            ProductDTO dto = new ProductDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setDescription(p.getDescription());
            dto.setPrice(p.getPrice());

            // Buscar categorías activas para este producto
            List<ProductCategory> pcs = productCategoryRepository.findByProductIdAndDeletedAtIsNull(p.getId());

            List<CategoryDTO> catDtos = pcs.stream()
                .map(pc -> {
                    Category c = pc.getCategory();
                    return new CategoryDTO(c.getId(), c.getName());
                })
                .collect(Collectors.toList());

            dto.setCategories(catDtos);

            dtos.add(dto);
        }

        return dtos;
    }

    // Busca un producto por su ID, solo si no ha sido eliminado lógicamente
    public Optional<Product> findById(int id) {
        return productRepository.findById(id)
                .filter(p -> p.getDeletedAt() == null);
    }

    // Guarda un nuevo producto o actualiza uno existente, realizando validaciones de negocio
    public Product save(Product product) {
        List<ObjectError> businessErrors = new ArrayList<>();

        // Valida que no exista otro producto con el mismo nombre (asumiendo unicidad por negocio)
        if (productRepository.existsByNameAndIdNot(product.getName(), product.getId() == null ? -1 : product.getId())) {
            businessErrors.add(new ObjectError("name", "The product name is already in use"));
        }

        if (!businessErrors.isEmpty()) {
            throw new BusinessValidationException(businessErrors);
        }

        return productRepository.save(product);
    }

    // Realiza el borrado lógico de un producto colocando la fecha actual en deletedAt
    @Transactional
    public boolean softDelete(int id) {
        return productRepository.findById(id).map(product -> {
            product.setDeletedAt(LocalDateTime.now());
            productRepository.save(product);
            return true;
        }).orElse(false);
    }
}
