package com.github.marceloasfilho.shoppingcart.service;

import com.github.marceloasfilho.shoppingcart.entity.Reserve;
import com.github.marceloasfilho.shoppingcart.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ReserveService {
    Optional<Reserve> getReserveById(Long id);

    Reserve save(Reserve reserve);

    BigDecimal getCartAmount(List<ShoppingCart> cartItems);
}
