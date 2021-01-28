package com.jandro.market.domain.service;

import com.jandro.market.domain.Purchase;
import com.jandro.market.domain.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> getAll() {
        return purchaseRepository.getAll();
    }

    public Optional<Purchase> get(int id) {
        return purchaseRepository.get(id);
    }

    public Optional<List<Purchase>> getByClient(String clientId) {
        return purchaseRepository.getByClient(clientId);
    }

    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public boolean delete(int id) {
        try {
            purchaseRepository.delete(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
