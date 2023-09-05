package com.github.marceloasfilho.eadCourse.repository;

import com.github.marceloasfilho.eadCourse.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

}