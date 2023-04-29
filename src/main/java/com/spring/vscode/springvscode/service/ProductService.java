package com.spring.vscode.springvscode.service;

import java.util.NoSuchElementException;

import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.vscode.springvscode.domain.Product;
import com.spring.vscode.springvscode.repository.ProductRepository;

import jakarta.el.MethodNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // listar todos
    public Iterable<Product> all() {
        return productRepository.findAll();
    }

    // salvar
    public Product insert(Product product) {
        return productRepository.save(product);
    }

    // busca por id
    public ResponseEntity<Product> byId(Long id) {
        return productRepository.findById(id).map(ResponseEntity::ok).orElseThrow();
    }

    // editar
    public Product edit(Long id, Product p) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Resource not found");
        }

        p.setId(id);
        return insert(p);
    }//

    // deletar
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Resource not found");
        }
        productRepository.deleteById(id);
    }
}
