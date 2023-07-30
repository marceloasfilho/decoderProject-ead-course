package com.ead.course.controller;

import com.ead.course.dto.CourseDTO;
import com.ead.course.model.CourseModel;
import com.ead.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<?> saveCourse(@RequestBody @Valid CourseDTO courseDTO) {
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDTO, courseModel);
        courseModel.setCreationDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        return new ResponseEntity<>(this.courseService.save(courseModel), HttpStatus.CREATED);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {
        Optional<CourseModel> courseModel = this.courseService.findById(courseId);
        if (courseModel.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
        this.courseService.delete(courseModel.get());
        return new ResponseEntity<>("Course deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable(value = "courseId") UUID courseId, @RequestBody @Valid CourseDTO courseDTO) {
        Optional<CourseModel> courseModel = this.courseService.findById(courseId);
        if (courseModel.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        var course = courseModel.get();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setImageUrl(courseDTO.getImageUrl());
        course.setCourseStatus(courseDTO.getCourseStatus());
        course.setCourseLevel(courseDTO.getCourseLevel());
        course.setLastUpdateDateTime(LocalDateTime.now(ZoneId.of("UTC")));

        return new ResponseEntity<>(this.courseService.save(course), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CourseModel>> getAllCourses() {
        return new ResponseEntity<>(this.courseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable(value = "courseId") UUID courseId) {
        Optional<CourseModel> courseModel = this.courseService.findById(courseId);
        return courseModel.isEmpty() ? new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND) : new ResponseEntity<>(courseModel.get(), HttpStatus.OK);
    }
}
