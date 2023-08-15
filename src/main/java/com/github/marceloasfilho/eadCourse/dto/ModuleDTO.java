package com.github.marceloasfilho.eadCourse.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class ModuleDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
