package com.github.marceloasfilho.shoppingcart.controller;

import com.github.marceloasfilho.shoppingcart.dto.CustomerDTO;
import com.github.marceloasfilho.shoppingcart.entity.Customer;
import com.github.marceloasfilho.shoppingcart.response.Response;
import com.github.marceloasfilho.shoppingcart.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(path = "/save")
    public ResponseEntity<Response<CustomerDTO>> save(@RequestBody @Valid CustomerDTO customerDTO, BindingResult bindingResult) {

        Customer customer = customerDTO.toModel();
        Customer savedCustomer = this.customerService.save(customer);

        Response<CustomerDTO> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        customerDTO.setId(savedCustomer.getId());
        response.setData(customerDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
