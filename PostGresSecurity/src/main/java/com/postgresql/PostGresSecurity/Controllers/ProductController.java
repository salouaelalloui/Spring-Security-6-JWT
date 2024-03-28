package com.postgresql.PostGresSecurity.Controllers;

import com.postgresql.PostGresSecurity.Entities.Product;
import com.postgresql.PostGresSecurity.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController

public class ProductController {
private final ProductService productService;


@GetMapping("/user/AllProducts")
    public ResponseEntity<List<Product>> getAllProducts (){
    Optional<List<Product>> optionalProducts= productService.getAllProducts();
    return optionalProducts.map(products-> ResponseEntity.ok(products)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
}
@GetMapping("/user/product/{id}")
    public Product getProductById(@PathVariable Long id){
    return productService.FindProductById(id);
}


//@PreAuthorize("hasAuthority('WRITE_PRODUCT_DATA')")
//@PreAuthorize("hasAuthority('WRITE_PRODUCT_DATA')")
@PostMapping("/user/createProduct")
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

}
