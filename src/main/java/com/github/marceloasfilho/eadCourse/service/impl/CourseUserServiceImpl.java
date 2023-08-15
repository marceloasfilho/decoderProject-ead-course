package com.github.marceloasfilho.eadCourse.service.impl;

import com.github.marceloasfilho.eadCourse.client.AuthuserClient;
import com.github.marceloasfilho.eadCourse.model.CourseModel;
import com.github.marceloasfilho.eadCourse.model.CourseUserModel;
import com.github.marceloasfilho.eadCourse.repository.CourseUserRepository;
import com.github.marceloasfilho.eadCourse.service.CourseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {
    private final CourseUserRepository courseUserRepository;
    private final AuthuserClient authuserClient;

    @Override
    public boolean existByCourseAndUserId(CourseModel courseModel, UUID userId) {
        return this.courseUserRepository.existsByCourseModelAndUserId(courseModel, userId);
    }

    @Override
    public CourseUserModel save(CourseUserModel courseUserModel) {
        return this.courseUserRepository.save(courseUserModel);
    }

    @Transactional
    @Override
    public CourseUserModel saveAndSendEnrollmentUserInCourse(CourseUserModel courseUserModel) {
        CourseUserModel save = this.save(courseUserModel);
        this.authuserClient.saveAndSendEnrollmentUserInCourse(courseUserModel.getCourseModel().getCourseId(), courseUserModel.getUserId());
        return save;
    }
}
