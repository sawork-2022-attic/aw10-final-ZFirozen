package com.micropos.carts.rest;

import com.micropos.api.CartsApi;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Product;
import com.micropos.carts.service.CartService;
import com.micropos.dto.ItemDto;
import com.micropos.dto.CartDto;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Item;
import com.micropos.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class CartController implements CartsApi {

    private final CartMapper cartMapper;

    private final CartService cartService;

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
        System.out.println("cart constructed");
    }

    @Override
    //@CrossOrigin
    public Mono<ResponseEntity<CartDto>> createCart(Mono<CartDto> cartDto, ServerWebExchange exchange) {

        return cartDto
                .map(cartMapper::toCart)
                .map(cart -> {
                    if (!cartService.addCart(cart))
                        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                    else
                        return new ResponseEntity<>(cartMapper.toCartDto(cart), HttpStatus.CREATED);
                });
    }

    @Override
    //@CrossOrigin
    public Mono<ResponseEntity<Flux<CartDto>>> listCarts(ServerWebExchange exchange) {
        List<CartDto> cartsDto = (List<CartDto>) cartMapper.toCartsDto(cartService.getCarts());
        return Mono.just(new ResponseEntity<>(Flux.fromIterable(cartsDto), HttpStatus.OK));
    }

    @Override
    //@CrossOrigin
    public Mono<ResponseEntity<CartDto>> showCartById(Integer cartId, ServerWebExchange exchange) {
        Cart cart = this.cartService.getCart(cartId);
        if (cart == null) {
            return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        CartDto cartDto = cartMapper.toCartDto(cart);
        return Mono.just(new ResponseEntity<>(cartDto, HttpStatus.OK));
    }

    @Override
    //@CrossOrigin
    public Mono<ResponseEntity<CartDto>> resetCartById(Integer cartId, ServerWebExchange exchange) {
        Cart cart = this.cartService.resetCart(cartId);
        if (cart == null) {
            return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        CartDto cartDto = cartMapper.toCartDto(cart);
        return Mono.just(new ResponseEntity<>(cartDto, HttpStatus.OK));
    }

//    @Override
//    public ResponseEntity<CartDto> addItemToCart(Integer userId, ItemDto itemDto) {
//        Item item = cartMapper.toItem(itemDto);
//        Cart cart = cartService.getCart(userId);
//        if (cart == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        cartService.add(userId, item);
//        CartDto cartDto = cartMapper.toCartDto(cart);
//        return new ResponseEntity<>(cartDto, HttpStatus.OK);
//    }

    @Override
    //@CrossOrigin
    public Mono<ResponseEntity<CartDto>> addProductToCart(Integer cartId,
                                                    Mono<ProductDto> productDto,
                                                    ServerWebExchange exchange
    ) {
        Cart cart = cartService.getCart(cartId);
        if (cart == null) {
            return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        return productDto
                .map(cartMapper::toProduct)
                .map(product -> { cartService.add(cartId, product); return cart; })
                .map(cartMapper::toCartDto)
                .map(cartDto -> new ResponseEntity<>(cartDto, HttpStatus.OK));
    }

    @Override
    //@CrossOrigin
    public Mono<ResponseEntity<Double>> showCartTotal(Integer cartId, ServerWebExchange exchange) {
        Cart cart = cartService.getCart(cartId);
        if (cart == null) {
            return Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        Double res = cartService.getTotal(cartId);
        //log.info("Get total res={} with ID {}", res, cartId);
        return Mono.just(new ResponseEntity<>(res, HttpStatus.OK));
    }
}
