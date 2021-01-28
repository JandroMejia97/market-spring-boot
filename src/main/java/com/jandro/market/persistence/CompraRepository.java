package com.jandro.market.persistence;

import com.jandro.market.domain.Purchase;
import com.jandro.market.domain.repository.PurchaseRepository;
import com.jandro.market.persistence.crud.CompraCrudRepository;
import com.jandro.market.persistence.entity.Compra;
import com.jandro.market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository  {
    @Autowired
    private CompraCrudRepository compraCrudRepository;
    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases((List<Compra>) compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        compra.getProductos().forEach(producto -> producto.setCompra(compra));
        return mapper.toPurchase(compraCrudRepository.save(compra));
    }

    @Override
    public Optional<Purchase> get(int id) {
        return compraCrudRepository.findById(id).map(purchase -> mapper.toPurchase(purchase));
    }

    @Override
    public void delete(int id) {
        compraCrudRepository.deleteById(id);
    }
}
