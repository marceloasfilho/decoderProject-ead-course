package com.github.marceloasfilho.eadCourse.service;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {
    String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable);

    String createUrlGetUserById(UUID userId);

    String createUrlSaveAndSendEnrollmentUserInCourse(UUID courseId, UUID userId);
}
