package com.jandro.market.web.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.jandro.market.domain.Purchase;
import com.jandro.market.domain.service.PurchaseService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation("Get all purchases on the market or all purchases from a specific customer.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Customer not found.")
    })
    public ResponseEntity<List<Purchase>> getAll(@RequestParam(name = "client", required = false) Optional<String> clientId) {
        if (clientId.isPresent())
            return purchaseService.getByClient(clientId.get())
                .filter(Predicate.not(List::isEmpty))
                .map(purchases -> new ResponseEntity<>(purchases, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(this.purchaseService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Save a purchase.")
    @ApiResponse(code = 201, message = "CREATED")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(this.purchaseService.save(purchase), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation("Search a purchase with an ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Purchase not found.")
    })
    public ResponseEntity<Purchase> get(@PathVariable("id") int id) {
        return ResponseEntity.of(purchaseService.get(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete a purchase with an ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Purchase not found.")
    })
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
        return purchaseService.delete(id) ? new ResponseEntity<Boolean>(HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
