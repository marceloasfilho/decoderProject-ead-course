package com.github.marceloasfilho.eadCourse.repository;

import com.github.marceloasfilho.eadCourse.model.CourseModel;
import com.github.marceloasfilho.eadCourse.model.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID> {
    boolean existsByCourseModelAndUserId(CourseModel courseModel, UUID userId);
}
