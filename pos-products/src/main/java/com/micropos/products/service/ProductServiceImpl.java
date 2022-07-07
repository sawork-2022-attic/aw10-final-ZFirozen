package com.micropos.products.service;

import com.micropos.products.model.Product;
import com.micropos.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> getProducts(String category, Integer page) {
        //return productRepository.getProducts(category, page);
        //return Flux.fromIterable(productRepository.findAll());
        if (page < 1 || page > 20)
            return Flux.empty();
        List<Product> products = new ArrayList<>();
        int i = 0;
        Random r = new Random();
        for (Product product: productRepository.findAll()) {
            int size = products.size();
//            if (r.nextDouble() < 0.01)
            if (i < 100) {
                products.add(product);
                ++i;
            }
        }
        System.out.println(products.size());
        return Flux.fromIterable(products);
    }

    @Override
    public Mono<Product> getProduct(String id) {
        //return productRepository.findProduct(id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            return Mono.empty();
        return Mono.just(optionalProduct.get());
    }

    @Override
    public Product randomProduct() {
        return null;
    }
}
