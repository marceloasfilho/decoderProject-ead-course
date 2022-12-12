package com.github.marceloasfilho.shoppingcart.service.implementation;

import com.github.marceloasfilho.shoppingcart.entity.Product;
import com.github.marceloasfilho.shoppingcart.entity.Reserve;
import com.github.marceloasfilho.shoppingcart.entity.ShoppingCart;
import com.github.marceloasfilho.shoppingcart.repository.ProductRepository;
import com.github.marceloasfilho.shoppingcart.repository.ReserveRepository;
import com.github.marceloasfilho.shoppingcart.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepository;
    private final ProductRepository productRepository;

    @Override
    public Optional<Reserve> getReserveById(Long id) {
        return this.reserveRepository.findById(id);
    }

    @Override
    public Reserve save(Reserve reserve) {
        return this.reserveRepository.save(reserve);
    }

    @Override
    public BigDecimal getCartAmount(List<ShoppingCart> cartItems) {

        BigDecimal singleCartAmount;
        BigDecimal totalCartAmount = BigDecimal.ZERO;
        int availableQuantity = 0;

        for (ShoppingCart cart : cartItems) {

            Optional<Product> productById = this.productRepository.findById(cart.getProductId());

            if (productById.isPresent()) {
                Product product = productById.get();
                log.info("Product " + product.getName() + " exists in database with id: " + product.getId());
                log.info("Product available quantity: " + product.getAvailableQuantity());

                if (product.getAvailableQuantity() < cart.getQuantity()) {
                    log.info("Required product quantity exceeds available quantity!");
                    singleCartAmount = product.getPrice().multiply(BigDecimal.valueOf(product.getAvailableQuantity()));
                    cart.setQuantity(product.getAvailableQuantity());
                } else {
                    singleCartAmount = product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
                    availableQuantity = product.getAvailableQuantity() - cart.getQuantity();
                    log.info("Product available quantity AFTER reserve: " + availableQuantity);
                    log.info("Product reserve cost: " + singleCartAmount);
                }

                totalCartAmount = totalCartAmount.add(singleCartAmount);
                log.info("Calculated amount of cart with id: " + cart.getId() + ": " + totalCartAmount);
                product.setAvailableQuantity(availableQuantity);
                cart.setProductName(product.getName());
                cart.setAmount(singleCartAmount);
                availableQuantity = 0;

                this.productRepository.save(product);
            }
        }
        return totalCartAmount;
    }
}
