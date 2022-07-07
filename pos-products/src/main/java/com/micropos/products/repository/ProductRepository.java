package com.micropos.products.repository;


import com.micropos.products.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

//public interface ProductRepository {
//
//    public List<Product> allProducts();
//
//    public Flux<Product> getProducts(String category, Integer page);
//
//    public Mono<Product> findProduct(String productId);
//
//}

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
}
