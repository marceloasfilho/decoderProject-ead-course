package com.github.marceloasfilho.shoppingcart.service;

import com.github.marceloasfilho.shoppingcart.entity.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findCustomerByEmail(String email);

    Customer save(Customer customer);
}
