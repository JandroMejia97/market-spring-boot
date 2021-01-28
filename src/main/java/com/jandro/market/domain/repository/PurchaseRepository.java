package com.jandro.market.domain.repository;

import com.jandro.market.domain.Product;
import com.jandro.market.domain.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {
    List<Purchase> getAll();
    Optional<List<Purchase>> getByClient(String clientId);
    Purchase save(Purchase purchase);
    Optional<Purchase> get(int id);
    void delete(int id);
}
