package com.github.marceloasfilho.shoppingcart.controller;

import com.github.marceloasfilho.shoppingcart.entity.Customer;
import com.github.marceloasfilho.shoppingcart.entity.Product;
import com.github.marceloasfilho.shoppingcart.entity.Reserve;
import com.github.marceloasfilho.shoppingcart.entity.ShoppingCart;
import com.github.marceloasfilho.shoppingcart.service.ReserveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReserveControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ReserveService reserveService;

    @Test
    public void deveRetornarReservePeloReserveId() throws Exception {
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

        when(this.reserveService.getReserveById(anyLong())).thenReturn(Optional.of(reserve));

        // Ação
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/reserve/getReserve/{reserveId}", reserve.getId()))
                // Verificação
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.description").value("Compras de fim de ano"))
                .andExpect(jsonPath("$.data.customer.id").value(1))
                .andExpect(jsonPath("$.data.customer.name").value("Bob Dylan"))
                .andExpect(jsonPath("$.data.customer.email").value("bob@yahoo.com"))
                .andExpect(jsonPath("$.data.cartItems[0].id").value(1))
                .andExpect(jsonPath("$.data.cartItems[0].productId").value(1))
                .andExpect(jsonPath("$.data.cartItems[0].productName").value("Samsung Galaxy S22 Ultra"))
                .andExpect(jsonPath("$.data.cartItems[0].quantity").value("3"))
                .andExpect(jsonPath("$.data.cartItems[0].amount").value("2"));
    }
}
