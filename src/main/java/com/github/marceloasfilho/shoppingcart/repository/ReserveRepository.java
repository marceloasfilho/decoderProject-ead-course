package com.github.marceloasfilho.shoppingcart.repository;

import com.github.marceloasfilho.shoppingcart.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
}
