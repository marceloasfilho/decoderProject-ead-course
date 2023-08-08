package com.ead.course.service.impl;

import com.ead.course.service.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    private static final String REQUEST_URI = "http://localhost:8087";

    @Override
    public String buildUrl(UUID courseId, Pageable pageable) {
        return REQUEST_URI +
                "/user/list?courseId=" +
                courseId +
                "&page=" +
                pageable.getPageNumber() +
                "&size=" +
                pageable.getPageSize() +
                "&sort=" +
                pageable.getSort().toString().replaceAll(": ", ",");
    }
}
