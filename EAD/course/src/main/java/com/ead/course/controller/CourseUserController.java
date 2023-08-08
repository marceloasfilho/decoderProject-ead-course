package com.ead.course.controller;

import com.ead.course.client.CourseClient;
import com.ead.course.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/course")
@RequiredArgsConstructor

public class CourseUserController {

    private final CourseClient courseClient;

    @GetMapping("/{courseId}/users")
    public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(
            @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(value = "courseId") UUID courseId) {

        Page<UserDTO> allUsersByCourse = this.courseClient.getAllUsersByCourse(courseId, pageable);
        return new ResponseEntity<>(allUsersByCourse, HttpStatus.OK);
    }
}
