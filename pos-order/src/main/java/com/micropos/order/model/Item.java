package com.micropos.order.model;

import com.micropos.carts.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {

    private int id;
    private int amount;
    private Product product;
}
