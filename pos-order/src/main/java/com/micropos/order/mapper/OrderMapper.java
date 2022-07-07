package com.micropos.order.mapper;

import com.micropos.order.model.Order;
import org.mapstruct.Mapper;
import com.micropos.dto.OrderDto;

import java.util.Collection;

@Mapper
public interface OrderMapper {

    OrderDto toOrderDto(Order order);

    Order toOrder(OrderDto orderDto);
}
