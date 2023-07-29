package com.ead.course.controller;

import com.ead.course.dto.ModuleDTO;
import com.ead.course.model.CourseModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.service.CourseService;
import com.ead.course.service.ModuleService;
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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;
    private final CourseService courseService;

    @PostMapping("/course/{courseId}/modules")
    public ResponseEntity<?> save(@PathVariable(value = "courseId") UUID courseId, @RequestBody @Valid ModuleDTO moduleDTO) {

        Optional<CourseModel> courseModel = this.courseService.findById(courseId);
        if (courseModel.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }

        var module = new ModuleModel();
        BeanUtils.copyProperties(moduleDTO, module);

        module.setCourse(courseModel.get());
        module.setCreationDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        return new ResponseEntity<>(this.moduleService.save(module), HttpStatus.CREATED);
    }

    @DeleteMapping("/course/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> delete(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId) {

        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);

        if (moduleModelOptional.isEmpty()) {
            return new ResponseEntity<>("Module not found for this course", HttpStatus.NOT_FOUND);
        }

        this.moduleService.delete(moduleModelOptional.get());
        return new ResponseEntity<>("Module deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/course/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> update(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId, @RequestBody @Valid ModuleDTO moduleDTO) {
        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);

        if (moduleModelOptional.isEmpty()) {
            return new ResponseEntity<>("Module not found for this course", HttpStatus.NOT_FOUND);
        }

        var module = moduleModelOptional.get();
        module.setTitle(moduleDTO.getTitle());
        module.setDescription(moduleDTO.getDescription());

        return new ResponseEntity<>(this.moduleService.save(module), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}/modules")
    public ResponseEntity<List<ModuleModel>> getAllModules(@PathVariable(value = "courseId") UUID courseId) {
        return new ResponseEntity<>(this.moduleService.findAllByCourse(courseId), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> getModule(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);
        return moduleModelOptional.isEmpty() ? new ResponseEntity<>("Module not found for this course", HttpStatus.NOT_FOUND) : new ResponseEntity<>(moduleModelOptional.get(), HttpStatus.OK);
    }
}
