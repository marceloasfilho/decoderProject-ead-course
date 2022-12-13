package com.github.marceloasfilho.shoppingcart.dto;

import com.github.marceloasfilho.shoppingcart.entity.Customer;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;

    public Customer toModel() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setName(this.name);
        customer.setEmail(this.email);
        return customer;
    }
}
