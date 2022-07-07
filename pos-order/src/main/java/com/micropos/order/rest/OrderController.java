package com.micropos.order.rest;

import com.micropos.api.OrderApi;
import com.micropos.dto.CartDto;
import com.micropos.dto.OrderDto;
import com.micropos.order.mapper.OrderMapper;
import com.micropos.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class OrderController implements OrderApi {

    private OrderService orderService;

    private OrderMapper orderMapper;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    //@CrossOrigin
    public Mono<ResponseEntity<OrderDto>> checkout(Mono<CartDto> cartDto, ServerWebExchange exchange) {
        return cartDto
                .flatMap(orderService::checkout)
                .map(orderMapper::toOrderDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }
}
