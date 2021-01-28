package com.jandro.market.web.controller;

import com.jandro.market.domain.Product;
import com.jandro.market.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(@RequestParam(name = "category") Optional<Integer> categoryId) {
        if (categoryId.isPresent())
            return productService.getByCategory(categoryId.get())
                    .filter(Predicate.not(List::isEmpty))
                    .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(this.productService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(this.productService.save(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable("id") int id) {
        return ResponseEntity.of(productService.get(id));
        /* **
        return this.productService.get(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
         */
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
        return productService.delete(id) ? new ResponseEntity<Boolean>(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
