package com.github.marceloasfilho.shoppingcart.dto;

import com.github.marceloasfilho.shoppingcart.entity.ShoppingCart;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ReserveInputDTO {
    private String description;
    @NotEmpty
    @NotNull
    private List<ShoppingCart> cartItems;
    private String customerName;
    @Email
    private String customerEmail;
}