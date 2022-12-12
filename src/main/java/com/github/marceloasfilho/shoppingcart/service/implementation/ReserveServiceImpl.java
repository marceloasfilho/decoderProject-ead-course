package com.github.marceloasfilho.shoppingcart.service.implementation;

import com.github.marceloasfilho.shoppingcart.entity.Reserve;
import com.github.marceloasfilho.shoppingcart.repository.ReserveRepository;
import com.github.marceloasfilho.shoppingcart.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepository;

    @Override
    public Optional<Reserve> getReserveById(Long id) {
        return this.reserveRepository.findById(id);
    }
}
