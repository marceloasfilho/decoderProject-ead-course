package com.ead.course.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LessonDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String videoUrl;
}
