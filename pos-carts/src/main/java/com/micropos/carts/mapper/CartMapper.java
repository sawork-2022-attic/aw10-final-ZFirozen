package com.micropos.carts.mapper;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Product;
import com.micropos.dto.ItemDto;
import com.micropos.dto.CartDto;
import com.micropos.carts.model.Item;
import com.micropos.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface CartMapper {

    Collection<CartDto> toCartsDto(Collection<Cart> carts);

    Collection<Cart> toCarts(Collection<CartDto> cartDtos);

    CartDto toCartDto(Cart cart);

    Cart toCart(CartDto cartDto);

    Collection<ItemDto> toItemsDto(Collection<Item> items);

    Collection<Item> toItems(Collection<ItemDto> itemDtos);

    Item toItem(ItemDto productDto);

    ItemDto toItemDto(Item item);

    Product toProduct(ProductDto productDto);

    ProductDto toPruductDto(Product product);
}
