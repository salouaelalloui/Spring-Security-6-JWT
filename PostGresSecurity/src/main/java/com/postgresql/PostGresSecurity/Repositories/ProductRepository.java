package com.postgresql.PostGresSecurity.Repositories;

import com.postgresql.PostGresSecurity.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
