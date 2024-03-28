package com.postgresql.PostGresSecurity.Services;

import com.postgresql.PostGresSecurity.Entities.Product;
import com.postgresql.PostGresSecurity.Repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    public Optional<List<Product>> getAllProducts(){

        List<Product> Products=productRepository.findAll();
        return Optional.ofNullable(Products);
    }

    public Product FindProductById(Long id) {
        return productRepository.findById(id).orElse(new Product());
               // .orElseThrow(() -> new RuntimeException("there is no product by this: " +  id));
    }
    public Product createProduct(Product product){
        return productRepository.save(product);
    }
}
