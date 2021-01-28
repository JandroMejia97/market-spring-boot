package com.jandro.market.web.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.jandro.market.domain.Purchase;
import com.jandro.market.domain.service.PurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<List<Purchase>> getAll(@RequestParam(name = "client") Optional<String> clientId) {
        if (clientId.isPresent())
            return purchaseService.getByClient(clientId.get())
                .filter(Predicate.not(List::isEmpty))
                .map(purchases -> new ResponseEntity<>(purchases, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(this.purchaseService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(this.purchaseService.save(purchase), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> get(@PathVariable("id") int id) {
        return ResponseEntity.of(purchaseService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
        return purchaseService.delete(id) ? new ResponseEntity<Boolean>(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
