package com.github.marceloasfilho.eadCourse.service.impl;

import com.github.marceloasfilho.eadCourse.service.CourseUserService;
import com.github.marceloasfilho.eadCourse.model.CourseModel;
import com.github.marceloasfilho.eadCourse.model.CourseUserModel;
import com.github.marceloasfilho.eadCourse.repository.CourseUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {
    private final CourseUserRepository courseUserRepository;

    @Override
    public boolean existByCourseAndUserId(CourseModel courseModel, UUID userId) {
        return this.courseUserRepository.existsByCourseModelAndUserId(courseModel, userId);
    }

    @Override
    public CourseUserModel save(CourseUserModel courseUserModel) {
        return this.courseUserRepository.save(courseUserModel);
    }
}
