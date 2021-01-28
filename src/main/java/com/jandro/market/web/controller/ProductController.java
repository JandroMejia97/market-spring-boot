package com.jandro.market.web.controller;

import com.jandro.market.domain.Product;
import com.jandro.market.domain.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation("Get all products on the market or all products in a specific category.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Category not found.")
    })
    public ResponseEntity<List<Product>> getAll(@ApiParam(value = "Category id", required = false, example = "2") @RequestParam(name = "category", required = false) Optional<Integer> categoryId) {
        if (categoryId.isPresent())
            return productService.getByCategory(categoryId.get())
                    .filter(Predicate.not(List::isEmpty))
                    .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(this.productService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Save a product.")
    @ApiResponse(code = 201, message = "CREATED")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(this.productService.save(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation("Search a product with an ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Product not found.")
    })
    public ResponseEntity<Product> get(@ApiParam(value = "Product id", required = true, example = "7") @PathVariable("id") int id) {
        return ResponseEntity.of(productService.get(id));
        /* **
        return this.productService.get(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
         */
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a product with an ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Product not found.")
    })
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
        return productService.delete(id) ? new ResponseEntity<Boolean>(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
