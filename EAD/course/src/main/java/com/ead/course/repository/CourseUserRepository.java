package com.ead.course.repository;

import com.ead.course.model.CourseModel;
import com.ead.course.model.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID> {
    boolean existsByCourseModelAndUserId(CourseModel courseModel, UUID userId);
}
