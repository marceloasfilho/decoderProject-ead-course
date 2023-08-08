package com.ead.course.client;

import com.ead.course.dto.ResponseGetAllUsersByCourseDTO;
import com.ead.course.dto.UserDTO;
import com.ead.course.service.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class CourseClient {

    private final RestTemplate restTemplate;
    private final UtilsService utilsService;

    public Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        String url = this.utilsService.buildUrl(courseId, pageable);
        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        ResponseEntity<ResponseGetAllUsersByCourseDTO<UserDTO>> bodyResponse;

        try {
            ParameterizedTypeReference<ResponseGetAllUsersByCourseDTO<UserDTO>> httpResponse = new ParameterizedTypeReference<>() {
            };
            bodyResponse = this.restTemplate.exchange(url, HttpMethod.GET, null, httpResponse);
            List<UserDTO> allUsersByCourse = bodyResponse.getBody().getContent();
            log.debug("Response Number of Elements: {} ", allUsersByCourse.size());
        } catch (HttpStatusCodeException httpStatusCodeException) {
            bodyResponse = null;
            log.error("Error request /course", httpStatusCodeException);
        }

        log.info("Ending request /user courseId: {}", courseId);

        return bodyResponse.getBody();
    }
}
