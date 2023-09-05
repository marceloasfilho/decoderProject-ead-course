package com.github.marceloasfilho.eadCourse.service;


import com.github.marceloasfilho.eadCourse.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {

    Page<UserModel> findAll(Specification<UserModel> specification, Pageable pageable);
}
