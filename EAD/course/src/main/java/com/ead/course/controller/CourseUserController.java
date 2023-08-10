package com.ead.course.controller;

import com.ead.course.client.AuthuserClient;
import com.ead.course.dto.EnrollmentDTO;
import com.ead.course.dto.UserDTO;
import com.ead.course.enums.UserStatus;
import com.ead.course.model.CourseModel;
import com.ead.course.model.CourseUserModel;
import com.ead.course.service.CourseService;
import com.ead.course.service.CourseUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseUserController {

    private final AuthuserClient authuserClient;
    private final CourseService courseService;
    private final CourseUserService courseUserService;

    @GetMapping("/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(
            @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(value = "courseId") UUID courseId) {

        Page<UserDTO> allUsersByCourse = this.authuserClient.getAllUsersByCourse(courseId, pageable);
        return new ResponseEntity<>(allUsersByCourse, HttpStatus.OK);
    }

    @PostMapping("/{courseId}/users/enroll")
    public ResponseEntity<?> saveUserEnrollmentInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                        @RequestBody @Valid EnrollmentDTO enrollmentDTO) {

        Optional<CourseModel> courseModel = this.courseService.findById(courseId);
        if (courseModel.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        if (this.courseUserService.existByCourseAndUserId(courseModel.get(), enrollmentDTO.getUserId())) {
            return new ResponseEntity<>("Error: User already enrolled in this course", HttpStatus.CONFLICT);
        }

        //todo verificação usuário ativo

        ResponseEntity<UserDTO> userById;
        try {
            userById = this.authuserClient.getUserById(enrollmentDTO.getUserId());

            if (userById.hasBody() && userById.getBody() != null) {
                if (userById.getBody().getUserStatus().equals(UserStatus.BLOCKED)) {
                    return new ResponseEntity<>("Error: User status is blocked", HttpStatus.CONFLICT);
                }
            }

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return new ResponseEntity<>("Error: User not found", HttpStatus.NOT_FOUND);
            }
        }

        var courseUserModel = new CourseUserModel();
        BeanUtils.copyProperties(courseModel.get(), courseUserModel);

        CourseUserModel saved = this.courseUserService.save(courseUserModel);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
