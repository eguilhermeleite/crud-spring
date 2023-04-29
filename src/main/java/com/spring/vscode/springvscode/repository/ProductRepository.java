package com.spring.vscode.springvscode.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.vscode.springvscode.domain.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> { 
    
}
