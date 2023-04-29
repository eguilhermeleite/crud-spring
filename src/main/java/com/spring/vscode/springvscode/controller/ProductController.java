package com.spring.vscode.springvscode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spring.vscode.springvscode.domain.Product;
import com.spring.vscode.springvscode.service.ProductService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Iterable<Product> showAll(){
        return productService.all() ;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> findBy(@PathVariable Long id){
        return productService.byId(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Transactional
    public Product insert(@Valid @RequestBody Product product){
        return productService.insert(product);
    }

    @PutMapping("/{id}")
    @Transactional
    public Product edit(@Valid @PathVariable Long id, @RequestBody Product product){
        return productService.edit(id , product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void del(@PathVariable Long id){
        productService.delete(id);
    }

}
