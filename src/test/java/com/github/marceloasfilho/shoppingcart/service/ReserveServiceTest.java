package com.github.marceloasfilho.shoppingcart.service;

import com.github.marceloasfilho.shoppingcart.entity.Customer;
import com.github.marceloasfilho.shoppingcart.entity.Reserve;
import com.github.marceloasfilho.shoppingcart.repository.ReserveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReserveServiceTest {
    @Autowired
    private ReserveService reserveService;

    @MockBean
    private ReserveRepository reserveRepository;

    @Test
    public void deveObterOrderPeloId() {
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
}
