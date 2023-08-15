package com.github.marceloasfilho.eadCourse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CourseUserDTO {
    private UUID courseId;
    private UUID userId;
}
