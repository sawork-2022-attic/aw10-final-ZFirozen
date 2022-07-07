package com.micropos.delivery;

import com.micropos.delivery.model.Order;
import com.micropos.delivery.service.DeliveryService;
import com.micropos.dto.CartDto;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;

public class OrderChecker implements Consumer<Order> {

    private DeliveryService service;

    public static final Logger log = LoggerFactory.getLogger(OrderChecker.class);

    private StreamBridge streamBridge;

    @Autowired
    public void setService(DeliveryService service) {
        this.service = service;
    }

    @Autowired
    public void setStreamBridge(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void accept(Order order) {
        service.addOrder(order);
        log.info("Deliver order with ID {}", order.getId());
        streamBridge.send("item-approved", message(order));
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }
}
