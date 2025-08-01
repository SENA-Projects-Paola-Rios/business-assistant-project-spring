package com.sena.BusinessAssistantSpring.service;

import com.sena.BusinessAssistantSpring.exception.BusinessValidationException;
import com.sena.BusinessAssistantSpring.model.Product;
import com.sena.BusinessAssistantSpring.repository.ProductRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Devuelve el listado de productos que no han sido eliminados (deletedAt es null)
    public List<Product> findAll() {
        return productRepository.findByDeletedAtIsNull();
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
