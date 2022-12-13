package com.github.marceloasfilho.shoppingcart.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marceloasfilho.shoppingcart.dto.CustomerDTO;
import com.github.marceloasfilho.shoppingcart.entity.Customer;
import com.github.marceloasfilho.shoppingcart.service.CustomerService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void deveRetornarCreatedAoSalvarUmCustomer() throws Exception {
        // Cenário
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Bob Dylan");
        customer.setEmail("bob@yahoo.com");

        when(this.customerService.save(any(Customer.class))).thenReturn(customer);

        // Ação
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/customer/save")
                                .content(this.getPayload(customer))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))

                // Verificação
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Bob Dylan"));
    }

    @Test
    public void deveRetornarBadRequestAoSalvarUmCustomerInvalido() throws Exception {
        // Cenário
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Bob Dylan");
        customer.setEmail("bob@yahoo.com");

        when(this.customerService.save(any(Customer.class))).thenReturn(customer);

        // Ação
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/customer/save")
                                .content("any invalid body")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))

                // Verificação
                .andExpect(status().isBadRequest());
    }

    private String getPayload(Customer customer) throws JsonProcessingException {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(customerDTO);
    }
}
