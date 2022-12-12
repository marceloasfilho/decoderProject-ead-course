package com.github.marceloasfilho.shoppingcart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.MERGE)
    private Customer customer;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = ShoppingCart.class)
    @JoinColumn(name = "reserve_id", referencedColumnName = "id")
    private List<ShoppingCart> cartItems;
}
