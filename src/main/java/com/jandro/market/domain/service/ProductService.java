package com.jandro.market.domain.service;

import com.jandro.market.domain.Product;
import com.jandro.market.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.getAll();
    }

    public Optional<Product> get(int id) {
        return productRepository.get(id);
    }

    public Optional<List<Product>> getByCategory(int categoryId) {
        return productRepository.getByCategory(categoryId);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public boolean delete(int id) {
        try {
            productRepository.delete(id);;
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

}
