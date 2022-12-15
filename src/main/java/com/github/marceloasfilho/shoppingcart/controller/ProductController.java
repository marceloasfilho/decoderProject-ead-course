package com.github.marceloasfilho.shoppingcart.controller;

import com.github.marceloasfilho.shoppingcart.dto.ProductDTO;
import com.github.marceloasfilho.shoppingcart.entity.Product;
import com.github.marceloasfilho.shoppingcart.response.Response;
import com.github.marceloasfilho.shoppingcart.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/save")
    @Transactional
    public ResponseEntity<Response<ProductDTO>> save(@RequestBody @Valid ProductDTO productDTO, BindingResult bindingResult) {

        Product product = productDTO.toModel();
        Product savedProduct = this.productService.save(product);

        Response<ProductDTO> response = new Response<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        productDTO.setId(savedProduct.getId());
        response.setData(productDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/getAllProducts")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> allProducts = this.productService.getAllProducts();

        List<ProductDTO> savedAllProducts = allProducts.stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setAvailableQuantity(product.getAvailableQuantity());
            productDTO.setPrice(product.getPrice());
            return productDTO;
        }).toList();

        return new ResponseEntity<>(savedAllProducts, HttpStatus.OK);
    }
}
