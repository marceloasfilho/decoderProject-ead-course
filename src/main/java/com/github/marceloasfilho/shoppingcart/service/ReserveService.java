package com.github.marceloasfilho.shoppingcart.service;

import com.github.marceloasfilho.shoppingcart.entity.Reserve;

import java.util.Optional;

public interface ReserveService {
    Optional<Reserve> getReserveById(Long id);
}
