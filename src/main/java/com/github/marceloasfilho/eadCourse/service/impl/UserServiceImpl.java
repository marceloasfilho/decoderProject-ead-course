package com.github.marceloasfilho.eadCourse.service.impl;

import com.github.marceloasfilho.eadCourse.model.UserModel;
import com.github.marceloasfilho.eadCourse.repository.UserRepository;
import com.github.marceloasfilho.eadCourse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<UserModel> findAll(Specification<UserModel> specification, Pageable pageable) {
        return this.userRepository.findAll(specification, pageable);
    }
}
