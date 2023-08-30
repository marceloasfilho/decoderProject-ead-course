package com.github.marceloasfilho.eadCourse.client;

import com.github.marceloasfilho.eadCourse.dto.CourseUserDTO;
import com.github.marceloasfilho.eadCourse.dto.ResponseGetAllUsersByCourseDTO;
import com.github.marceloasfilho.eadCourse.dto.UserDTO;
import com.github.marceloasfilho.eadCourse.service.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthuserClient {
    private final RestTemplate restTemplate;
    private final UtilsService utilsService;
    @Value("${ead.api.url.authuser}")
    private String REQUEST_URI_API_AUTHUSER;

    public Page<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        String url = REQUEST_URI_API_AUTHUSER.concat(this.utilsService.createUrlGetAllUsersByCourse(courseId, pageable));
        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        ResponseEntity<ResponseGetAllUsersByCourseDTO<UserDTO>> bodyResponse;

        try {
            ParameterizedTypeReference<ResponseGetAllUsersByCourseDTO<UserDTO>> httpResponse = new ParameterizedTypeReference<>() {
            };
            bodyResponse = this.restTemplate.exchange(url, HttpMethod.GET, null, httpResponse);
            List<UserDTO> allUsersByCourse = Objects.requireNonNull(bodyResponse.getBody()).getContent();
            log.debug("Response Number of Elements: {} ", allUsersByCourse.size());
        } catch (HttpStatusCodeException httpStatusCodeException) {
            bodyResponse = null;
            log.error("Error request /course", httpStatusCodeException);
        }

        log.info("Ending request /user courseId: {}", courseId);

        return Objects.requireNonNull(bodyResponse).getBody();
    }


    public ResponseEntity<UserDTO> getUserById(UUID userId) {
        String url = REQUEST_URI_API_AUTHUSER.concat(this.utilsService.createUrlGetUserById(userId));
        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);
        return this.restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);
    }

    public void saveAndSendEnrollmentUserInCourse(UUID courseId, UUID userId) {
        String url = REQUEST_URI_API_AUTHUSER.concat(this.utilsService.createUrlSaveAndSendEnrollmentUserInCourse(courseId, userId));
        var courseUserDTO = new CourseUserDTO(courseId, userId);
        this.restTemplate.postForObject(url, courseUserDTO, String.class);
    }

    public void deleteUserCourseIntoAuthuser(UUID courseId) {
        String url = REQUEST_URI_API_AUTHUSER.concat(this.utilsService.createUrlDeleteUserCourseIntoAuthuser(courseId));
        this.restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
