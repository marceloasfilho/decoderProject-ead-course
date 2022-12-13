package com.github.marceloasfilho.shoppingcart.dto;

import com.github.marceloasfilho.shoppingcart.entity.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Integer availableQuantity;
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
