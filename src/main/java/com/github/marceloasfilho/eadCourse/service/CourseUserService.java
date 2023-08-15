package com.github.marceloasfilho.eadCourse.service;


import com.github.marceloasfilho.eadCourse.model.CourseModel;
import com.github.marceloasfilho.eadCourse.model.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {
    boolean existByCourseAndUserId(CourseModel courseModel, UUID userId);

    CourseUserModel save(CourseUserModel courseUserModel);
}
