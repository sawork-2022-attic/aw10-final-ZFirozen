package com.micropos.products.service;

import com.micropos.products.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductService {

//    public List<Product> products();

    public Flux<Product> getProducts(String category, Integer page);

    public Mono<Product> getProduct(String id);

    public Product randomProduct();
}
