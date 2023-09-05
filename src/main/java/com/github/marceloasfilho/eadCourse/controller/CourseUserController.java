package com.github.marceloasfilho.eadCourse.controller;

import com.github.marceloasfilho.eadCourse.dto.EnrollmentDTO;
import com.github.marceloasfilho.eadCourse.model.CourseModel;
import com.github.marceloasfilho.eadCourse.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseUserController {

    private final CourseService courseService;

    @GetMapping("/{courseId}/users")
    public ResponseEntity<?> getAllUsersByCourse(
            @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(value = "courseId") UUID courseId) {
        // todo verificações de state transfer

        Optional<CourseModel> courseModel = this.courseService.findById(courseId);
        if (courseModel.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/{courseId}/users/enroll")
    public ResponseEntity<?> saveUserEnrollmentInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                        @RequestBody @Valid EnrollmentDTO enrollmentDTO) {
        // todo verificações de state transfer

        Optional<CourseModel> courseModel = this.courseService.findById(courseId);
        if (courseModel.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
