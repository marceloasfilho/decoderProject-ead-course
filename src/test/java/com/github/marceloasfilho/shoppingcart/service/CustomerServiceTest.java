package com.github.marceloasfilho.shoppingcart.service;

import com.github.marceloasfilho.shoppingcart.entity.Customer;
import com.github.marceloasfilho.shoppingcart.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void deveEncontrarUmCustomerPorEmail() {
        // Cenário
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Bob Dylan");
        customer.setEmail("bob@yahoo.com");

        when(this.customerRepository.findCustomerByEmail(anyString())).thenReturn(Optional.of(customer));

        // Ação
        Optional<Customer> customerByEmail = this.customerService.findCustomerByEmail(anyString());

        // Verificação
        assertTrue(customerByEmail.isPresent());
    }

    @Test
    public void deveSalvarUmCustomer() {
        // Cenário
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Bob Dylan");
        customer.setEmail("bob@yahoo.com");

        when(this.customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Ação
        Customer savedCustomer = this.customerService.save(customer);

        // Verificação
        assertNotNull(savedCustomer);
        assertEquals("Bob Dylan", savedCustomer.getName());
    }
}
