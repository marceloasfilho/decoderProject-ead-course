package com.github.marceloasfilho.shoppingcart.service;

import com.github.marceloasfilho.shoppingcart.entity.Product;
import com.github.marceloasfilho.shoppingcart.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void deveEncontrarTodosOsProdutos() {
        // Cenário
        Product product = new Product();
        product.setId(1L);
        product.setName("Samsung Galaxy S22 Ultra");
        product.setPrice(BigDecimal.valueOf(5500.00));
        product.setAvailableQuantity(5);

        when(this.productRepository.findAll()).thenReturn(List.of(product));

        // Ação
        List<Product> allProducts = this.productService.getAllProducts();

        // Verificação
        int savedAvailableQuantity = allProducts.get(0).getAvailableQuantity();
        assertEquals(5, savedAvailableQuantity);
        assertFalse(allProducts.isEmpty());
    }

    @Test
    public void deveSalvarUmProduto() {
        // Cenário
        Product product = new Product();
        product.setId(1L);
        product.setName("Samsung Galaxy S22 Ultra");
        product.setPrice(BigDecimal.valueOf(5500.00));
        product.setAvailableQuantity(5);

        when(this.productRepository.save(any(Product.class))).thenReturn(product);

        // Ação
        Product savedProduct = this.productService.save(product);

        // Verificação
        assertNotNull(savedProduct);
        assertEquals(5, savedProduct.getAvailableQuantity().intValue());
    }
}
