package com.micropos.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.dto.CartDto;
import com.micropos.dto.ItemDto;
import com.micropos.order.model.Cart;
import com.micropos.order.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderService {

    public static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private StreamBridge streamBridge;

    //private RestTemplate restTemplate;

    private final WebClient webClient;

    private final ReactiveCircuitBreaker circuitBreaker;

    private int orderCnt = 0;

    public OrderService(ReactiveCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080/api").build();
        this.circuitBreaker = circuitBreakerFactory.create("carts");
    }

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

//    @Autowired
//    public void setRestTemplate(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    public Mono<Order> checkout(CartDto cartDto) {

        int cartId = cartDto.getId();
        log.info("Checkout cart with ID {}", cartId);
        return circuitBreaker.run(
                webClient.get()
                    .uri("/carts/" + cartId + "/total")
                    .retrieve()
                    .bodyToMono(Double.class)
                    .map(total -> {
                        Order order = new Order(orderCnt++, total, cartDto);
                        streamBridge.send("OrderDeliverer", order);
                        return order;
                    }),
                    throwable -> {
                        log.warn("Error making request to cart service", throwable);
                        return Mono.empty();
                    });
//        String url = "http://localhost:8080/api/carts/" +cartId + "/total";
//        try {
//            Double res = restTemplate.getForObject(url, Double.class);
//            if (res == null)
//                return -1;
//            return res;
//        }
//        catch (Exception e) {
//            return -1;
//        }
    }
}
