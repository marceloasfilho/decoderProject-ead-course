package com.github.marceloasfilho.shoppingcart.controller;

import com.github.marceloasfilho.shoppingcart.dto.ReserveInputDTO;
import com.github.marceloasfilho.shoppingcart.dto.ReserveOutputDTO;
import com.github.marceloasfilho.shoppingcart.entity.Customer;
import com.github.marceloasfilho.shoppingcart.entity.Reserve;
import com.github.marceloasfilho.shoppingcart.response.Response;
import com.github.marceloasfilho.shoppingcart.service.CustomerService;
import com.github.marceloasfilho.shoppingcart.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping(path = "/reserve")
@RequiredArgsConstructor
@Slf4j
public class ReserveController {
    private final ReserveService reserveService;
    private final CustomerService customerService;

    @GetMapping(path = "/getReserve/{reserveId}")
    public ResponseEntity<Response<Reserve>> getReserveById(@PathVariable("reserveId") Long reserveId) {
        Optional<Reserve> reserveById = this.reserveService.getReserveById(reserveId);

        Response<Reserve> response = new Response<>();

        if (reserveById.isPresent()) {
            response.setData(reserveById.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping(path = "/save")
    public ResponseEntity<Response<ReserveOutputDTO>> save(@RequestBody ReserveInputDTO reserveInputDTO, BindingResult bindingResult) {
        log.info("Body Request: " + reserveInputDTO.toString());

        ReserveOutputDTO reserveOutputDTO = new ReserveOutputDTO();

        log.info("Finding Customer by email: " + reserveInputDTO.getCustomerEmail());
        Optional<Customer> customerByEmail = this.customerService.findCustomerByEmail(reserveInputDTO.getCustomerEmail());

        Customer customer = new Customer();

        if (customerByEmail.isPresent()) {
            log.info("Customer already exists in database with id: " + customerByEmail.get().getId());
            customer = customerByEmail.get();
        } else {
            customer.setName(reserveInputDTO.getCustomerName());
            customer.setEmail(reserveInputDTO.getCustomerEmail());
            Customer savedCustomer = this.customerService.save(customer);
            log.info("Customer saved with id: " + savedCustomer.getId());
        }

        Reserve reserve = new Reserve();
        reserve.setDescription(reserveInputDTO.getDescription());
        reserve.setCustomer(customer);
        reserve.setCartItems(reserveInputDTO.getCartItems());

        Reserve savedReserve = this.reserveService.save(reserve);
        log.info("Reserve saved with id: " + savedReserve.getId());

        reserveOutputDTO.setReserveDescription(reserveInputDTO.getDescription());
        reserveOutputDTO.setDateTime(LocalDateTime.now());
        reserveOutputDTO.setInvoiceNumber(new Random().nextInt(1000));
        reserveOutputDTO.setAmount(this.reserveService.getCartAmount(reserveInputDTO.getCartItems()));
        log.info("Reserve output created!");

        Response<ReserveOutputDTO> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.setData(reserveOutputDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
