package com.github.marceloasfilho.eadCourse.repository;

import com.github.marceloasfilho.eadCourse.model.CourseModel;
import com.github.marceloasfilho.eadCourse.model.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID> {
    boolean existsByCourseModelAndUserId(CourseModel courseModel, UUID userId);

    @Query(value = "SELECT * FROM tb_course_user WHERE course_id =:courseId", nativeQuery = true)
    List<CourseUserModel> findAllCourseUserIntoCourse(@Param("courseId") UUID courseId);

    boolean existsByUserId(UUID userId);

    void deleteAllByUserId(UUID userId);
}
