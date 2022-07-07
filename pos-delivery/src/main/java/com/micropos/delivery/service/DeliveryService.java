package com.micropos.delivery.service;

import com.micropos.delivery.model.Order;
import com.micropos.dto.ItemDto;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryService {

    private static class OrderItem {

        private final long startTime;
        private final Order order;

        public OrderItem(long startTime, Order order) {
            this.startTime = startTime;
            this.order = order;
        }

        public String outputStatus(long currTime) {
            long diff = currTime - startTime;
            if (diff < 0)
                return "Time Error";
            List<ItemDto> cartItems = order.getCartDto().getItems();
            long step = 60 * 1000 / cartItems.size();
            long i = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (ItemDto itemDto: cartItems) {
                if (i < diff) {
                    stringBuilder.append("DONE:\n");
                }
                else
                    stringBuilder.append("WAITING:\n");
                stringBuilder.append(itemDto.toString());
                stringBuilder.append('\n');
                i = i + step;
            }
            return stringBuilder.toString();
        }
    }

    private static class StatusResponse {

        private String status;

        public StatusResponse(String status) {
            this.status = status;
        }
    }

    private final Map<Integer, OrderItem> orders = new HashMap<>();

    public void addOrder(Order order) {

        orders.put(order.getId(), new OrderItem(System.currentTimeMillis(), order));
    }

//    @Bean
//    public IntegrationFlow outGate() {
//        return IntegrationFlows.from("deliveryCheckChannel")
//                .<Integer, String>transform(orderId -> {
//                    if (orderId < 0)
//                        return "Invalid order ID";
//                    OrderItem item = orders.get(orderId);
//                    if (item == null)
//                        return "Invalid order ID";
//                    return item.outputStatus(System.currentTimeMillis());
//                })
//                .get();
//    }

    @Bean
    public IntegrationFlow inGate() {
        return IntegrationFlows.from(
                        Http.inboundGateway("/api/check/{orderId}")
                                .headerExpression("orderId", "#pathVariables.orderId")
                )
                .headerFilter("accept-encoding", false)
                .handle(((payload, headers) -> {
                    Object orderId = headers.get("orderId");
                    int id = orderId == null? -1: Integer.parseInt((String) orderId);
                    if (id < 0)
                        return "Invalid order ID";
                    OrderItem item = orders.get(id);
                    if (item == null)
                        return "Invalid order ID";
                    return item.outputStatus(System.currentTimeMillis());
                }))
//                .channel("deliveryCheckChannel")
                .get();
    }
}
