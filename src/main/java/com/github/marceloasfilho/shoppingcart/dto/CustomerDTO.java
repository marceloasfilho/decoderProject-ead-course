package com.github.marceloasfilho.shoppingcart.dto;

import com.github.marceloasfilho.shoppingcart.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    @NotNull(message = "Invalid name: NULL")
    @NotEmpty(message = "Invalid name: EMPTY")
    @Size(min = 3, max = 30, message = "Invalid name: Must be of 3 - 30 characters")
    private String name;
    @Email(message = "Invalid email")
    private String email;

    public Customer toModel() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setName(this.name);
        customer.setEmail(this.email);
        return customer;
    }
}
