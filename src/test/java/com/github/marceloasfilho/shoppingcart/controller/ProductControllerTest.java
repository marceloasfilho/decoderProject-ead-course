package com.github.marceloasfilho.shoppingcart.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marceloasfilho.shoppingcart.dto.ProductDTO;
import com.github.marceloasfilho.shoppingcart.entity.Product;
import com.github.marceloasfilho.shoppingcart.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void deveRetornarCreatedAoSalvarUmCustomer() throws Exception {
        // Cenário
        Product product = new Product();
        product.setId(1L);
        product.setName("Samsung Galaxy S22 Ultra");
        product.setPrice(BigDecimal.valueOf(5500.00));
        product.setAvailableQuantity(5);

        when(this.productService.save(any(Product.class))).thenReturn(product);

        // Ação
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/product/save")
                                .content(this.getPayload(product))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))

                // Verificação
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Samsung Galaxy S22 Ultra"))
                .andExpect(jsonPath("$.data.availableQuantity").value(5))
                .andExpect(jsonPath("$.data.price").value(BigDecimal.valueOf(5500.00)));
    }

    @Test
    public void deveRetornarBadRequestAoSalvarUmCustomerInvalido() throws Exception {
        // Cenário
        Product product = new Product();
        product.setId(1L);
        product.setName("Samsung Galaxy S22 Ultra");
        product.setPrice(BigDecimal.valueOf(5500.00));
        product.setAvailableQuantity(5);

        when(this.productService.save(any(Product.class))).thenReturn(product);

        // Ação
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/product/save")
                                .content("any invalid body")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))

                // Verificação
                .andExpect(status().isBadRequest());
    }

    private String getPayload(Product product) throws JsonProcessingException {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setAvailableQuantity(product.getAvailableQuantity());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(productDTO);
    }
}
