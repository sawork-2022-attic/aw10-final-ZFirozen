package com.micropos.carts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.carts.model.Product;
import com.micropos.carts.repository.Carts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CartService {

    private CartMapper cartMapper;
    private Carts cartRepository;

    //private RestTemplate restTemplate;

    @Autowired
    public void setCartRepository(Carts cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

//    @Autowired
//    public void setRestTemplate(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    public boolean addCart(Cart cart) {
        return cartRepository.addCart(cart);
    }

    public List<Cart> getCarts() {
        return cartRepository.getCarts();
    }

    @Nullable
    public Cart getCart(int userId) {
        return cartRepository.getCart(userId);
    }

    @Nullable
    public Cart resetCart(int userId) {
        return cartRepository.resetCart(userId);
    }

    public Item getItem(int userId, String productId) {
        return cartRepository.getItem(userId, productId);
    }

    public boolean add(int userId, Item item) {
        return cartRepository.addItem(userId, item);
    }

    public boolean add(int userId, Product product) {
        return cartRepository.addProduct(userId, product);
    }

    public boolean remove(int userId, String productId) {
        return cartRepository.removeProduct(userId, productId);
    }

    public double getTotal(int userId) {
        return cartRepository.getTotal(userId);
    }
}
