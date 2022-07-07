package com.micropos.order.model;

import com.micropos.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Order {

    private int id;
    private double total;
    private CartDto cartDto;

    public Order(int id, double total, CartDto cartDto) {
        this.id = id;
        this.total = total;
        this.cartDto = cartDto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CartDto getCartDto() {
        return cartDto;
    }

    public void setCartDto(CartDto cartDto) {
        this.cartDto = cartDto;
    }
}
