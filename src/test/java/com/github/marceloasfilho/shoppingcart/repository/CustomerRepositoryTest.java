package com.github.marceloasfilho.shoppingcart.repository;

import com.github.marceloasfilho.shoppingcart.entity.Customer;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @After
    public void close(){
        this.customerRepository.deleteAll();
    }

    @Test
    public void deveSalvarUmCustomer(){
        // Cenário
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Bob Dylan");
        customer.setEmail("bob@yahoo.com");

        // Ação
        Customer save = this.customerRepository.save(customer);
        Optional<Customer> foundCustomer = this.customerRepository.findById(save.getId());

        // Verificação
        assertTrue(foundCustomer.isPresent());
        assertEquals("Bob Dylan", foundCustomer.get().getName());
        assertEquals("bob@yahoo.com", foundCustomer.get().getEmail());
    }

    @Test
    public void deveEncontrarUmCustomerPorEmail(){
        // Cenário
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Bob Dylan");
        customer.setEmail("bob@yahoo.com");

        // Ação
        Optional<Customer> customerByEmail = this.customerRepository.findCustomerByEmail(customer.getEmail());

        // Verificação
        assertTrue(customerByEmail.isPresent());
        assertEquals("Bob Dylan", customerByEmail.get().getName());
    }
}
