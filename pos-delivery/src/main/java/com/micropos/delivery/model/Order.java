package com.micropos.delivery.model;

import com.micropos.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Order {

    private int id;
    private double total;
    private CartDto cartDto;
}
