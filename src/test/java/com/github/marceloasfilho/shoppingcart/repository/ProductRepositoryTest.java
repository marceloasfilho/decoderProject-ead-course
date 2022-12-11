package com.github.marceloasfilho.shoppingcart.repository;

import com.github.marceloasfilho.shoppingcart.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void deveEncontrarTodosOsProdutos() {
        // Cenário
        Product product = new Product();
        product.setId(1L);
        product.setName("Samsung Galaxy S22 Ultra");
        product.setPrice(BigDecimal.valueOf(5500.00));
        product.setAvailableQuantity(5);

        // Ação
        this.productRepository.save(product);
        List<Product> allProducts = this.productRepository.findAll();

        // Verificação
        int savedAvailableQuantity = allProducts.get(0).getAvailableQuantity();
        assertEquals(5, savedAvailableQuantity);
        assertFalse(allProducts.isEmpty());
    }
}
