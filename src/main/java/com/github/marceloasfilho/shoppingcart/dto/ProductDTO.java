package com.github.marceloasfilho.shoppingcart.dto;

import com.github.marceloasfilho.shoppingcart.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    @NotNull(message = "Invalid name: NULL")
    @NotBlank(message = "Invalid name: BLANK")
    @Size(min = 3, max = 30, message = "Invalid name: Must be of 3 - 30 characters")
    private String name;
    @NotNull(message = "Invalid availableQuantity: NULL")
    private Integer availableQuantity;
    @NotNull(message = "Invalid price: NULL")
    private BigDecimal price;

    public Product toModel() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setAvailableQuantity(this.availableQuantity);
        product.setPrice(this.price);
        return product;
    }
}
