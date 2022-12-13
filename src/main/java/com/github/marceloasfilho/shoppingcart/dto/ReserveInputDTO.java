package com.github.marceloasfilho.shoppingcart.dto;

import com.github.marceloasfilho.shoppingcart.entity.ShoppingCart;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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