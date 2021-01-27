package com.jandro.market.domain.repository;

import com.jandro.market.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAll();
    Optional<List<Product>> getByCategory(int categoryId);
    Optional<List<Product>> getScarceProduct(int quantity);
    Optional<Product> get(int id);
    Product save(Product product);
    void delete(int id);
}
