package com.github.marceloasfilho.shoppingcart.repository;

import com.github.marceloasfilho.shoppingcart.entity.Customer;
import com.github.marceloasfilho.shoppingcart.entity.Product;
import com.github.marceloasfilho.shoppingcart.entity.Reserve;
import com.github.marceloasfilho.shoppingcart.entity.ShoppingCart;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReserveRepositoryTest {
    @Autowired
    private ReserveRepository reserveRepository;

    @After
    public void close() {
        this.reserveRepository.deleteAll();
    }

    @Test
    public void deveObterReservePeloId() {
        // Cenário
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Bob Dylan");
        customer.setEmail("bob@yahoo.com");

        Product product = new Product();
        product.setId(1L);
        product.setName("Samsung Galaxy S22 Ultra");
        product.setPrice(BigDecimal.valueOf(5500.00));
        product.setAvailableQuantity(5);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setAmount(BigDecimal.TWO);
        shoppingCart.setQuantity(3);
        shoppingCart.setProductId(product.getId());
        shoppingCart.setProductName(product.getName());

        Reserve reserve = new Reserve();
        reserve.setId(1L);
        reserve.setDescription("Compras de fim de ano");
        reserve.setCustomer(customer);
        reserve.setCartItems(List.of(shoppingCart));

        // Ação
        Reserve save = this.reserveRepository.save(reserve);
        Optional<Reserve> reserveById = this.reserveRepository.findById(save.getId());

        // Verificação
        assertTrue(reserveById.isPresent());
        assertEquals("Compras de fim de ano", reserveById.get().getDescription());
    }
}
