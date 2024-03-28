package com.postgresql.PostGresSecurity.Services;

import com.postgresql.PostGresSecurity.Entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Optional<List<Product>> getAllProducts();
    public Product FindProductById(Long id);
    public Product createProduct(Product product);
}
