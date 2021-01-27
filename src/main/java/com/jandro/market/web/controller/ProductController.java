package com.jandro.market.web.controller;

import com.jandro.market.domain.Product;
import com.jandro.market.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll(@RequestParam(name = "category") Optional<Integer> categoryId) {
        if (categoryId.isPresent())
            return productService.getByCategory(categoryId.get()).orElseGet(() -> null);
        return this.productService.getAll();
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return this.productService.save(product);
    }

    @GetMapping("/{id}")
    public Optional<Product> get(@PathVariable("id") int id) {
        return this.productService.get(id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return productService.delete(id);
    }

}
