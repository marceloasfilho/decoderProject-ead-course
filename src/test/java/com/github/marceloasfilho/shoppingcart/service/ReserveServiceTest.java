package com.github.marceloasfilho.shoppingcart.service;

import com.github.marceloasfilho.shoppingcart.entity.Customer;
import com.github.marceloasfilho.shoppingcart.entity.Product;
import com.github.marceloasfilho.shoppingcart.entity.Reserve;
import com.github.marceloasfilho.shoppingcart.entity.ShoppingCart;
import com.github.marceloasfilho.shoppingcart.repository.ProductRepository;
import com.github.marceloasfilho.shoppingcart.repository.ReserveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReserveServiceTest {
    @Autowired
    private ReserveService reserveService;

    @MockBean
    private ReserveRepository reserveRepository;
    @MockBean
    private ProductRepository productRepository;

    @Test
    public void deveObterReservePeloId() {
        // Cenário
        Reserve reserve = new Reserve();
        reserve.setId(1L);
        reserve.setDescription("Compras de fim de ano");
        reserve.setCustomer(new Customer());
        reserve.setCartItems(new ArrayList<>());

        when(this.reserveRepository.findById(anyLong())).thenReturn(Optional.of(reserve));

        // Ação
        Optional<Reserve> reserveById = this.reserveService.getReserveById(reserve.getId());

        // Verificação
        assertTrue(reserveById.isPresent());
        assertEquals(1L, reserveById.get().getId().longValue());
    }

    @Test
    public void deveSalvarReserve() {
        // Cenário
        Reserve reserve = new Reserve();
        reserve.setId(1L);
        reserve.setDescription("Compras de fim de ano");
        reserve.setCustomer(new Customer());
        reserve.setCartItems(new ArrayList<>());

        when(this.reserveRepository.save(any(Reserve.class))).thenReturn(reserve);

        // Ação
        Reserve save = this.reserveService.save(reserve);

        // Verificação
        assertNotNull(save);
        assertEquals("Compras de fim de ano", save.getDescription());
    }

    @Test
    public void deveObterValorTotalCarrinhosCompra() {

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

        when(this.productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // Ação
        BigDecimal cartAmount = this.reserveService.getCartAmount(reserve.getCartItems());

        // Verificação

        assertEquals(BigDecimal.valueOf(16500.0), cartAmount);
    }
}
