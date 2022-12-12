package com.github.marceloasfilho.shoppingcart.controller;

import com.github.marceloasfilho.shoppingcart.entity.Reserve;
import com.github.marceloasfilho.shoppingcart.entity.Product;
import com.github.marceloasfilho.shoppingcart.response.Response;
import com.github.marceloasfilho.shoppingcart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ProductService productService;

    @GetMapping(path = "/getAllProducts")
    public ResponseEntity<Response<List<Product>>> getAllProducts(BindingResult bindingResult) {
        List<Product> allProducts = this.productService.getAllProducts();

        Response<List<Product>> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.setData(allProducts);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/getOrder/{orderId}")
    public ResponseEntity<Response<Reserve>> getOrderById(@PathVariable("orderId") int orderId){
        return null;
    }
}
