package com.github.marceloasfilho.eadCourse.service.impl;

import com.github.marceloasfilho.eadCourse.service.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {
    @Override
    public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable) {
        return "/user/list?courseId=" +
                courseId +
                "&page=" +
                pageable.getPageNumber() +
                "&size=" +
                pageable.getPageSize() +
                "&sort=" +
                pageable.getSort().toString().replaceAll(": ", ",");
    }

    @Override
    public String createUrlGetUserById(UUID userId) {
        return "/user/getUser/".concat(String.valueOf(userId));
    }

    @Override
    public String createUrlSaveAndSendEnrollmentUserInCourse(UUID courseId, UUID userId) {
        return "/user/".concat(String.valueOf(userId)).concat("/courses/enroll");
    }

    @Override
    public String createUrlDeleteUserCourseIntoAuthuser(UUID courseId) {
        return "/user/course/".concat(String.valueOf(courseId));
    }
}
