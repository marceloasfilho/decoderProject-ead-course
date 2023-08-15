package com.github.marceloasfilho.eadCourse.controller;

import com.github.marceloasfilho.eadCourse.dto.ModuleDTO;
import com.github.marceloasfilho.eadCourse.model.CourseModel;
import com.github.marceloasfilho.eadCourse.model.ModuleModel;
import com.github.marceloasfilho.eadCourse.service.CourseService;
import com.github.marceloasfilho.eadCourse.service.ModuleService;
import com.github.marceloasfilho.eadCourse.specification.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ModuleController {
    private final ModuleService moduleService;
    private final CourseService courseService;

    @PostMapping("/course/{courseId}/modules")
    public ResponseEntity<?> saveModule(@PathVariable(value = "courseId") UUID courseId, @RequestBody @Valid ModuleDTO moduleDTO) {

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
    public ResponseEntity<?> deleteModule(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId) {

        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);

        if (moduleModelOptional.isEmpty()) {
            return new ResponseEntity<>("Module not found for this course", HttpStatus.NOT_FOUND);
        }

        this.moduleService.delete(moduleModelOptional.get());
        return new ResponseEntity<>("Module deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/course/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> updateModule(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId, @RequestBody @Valid ModuleDTO moduleDTO) {
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
    public ResponseEntity<Page<ModuleModel>> getAllModules(@PathVariable(value = "courseId") UUID courseId,
                                                           SpecificationTemplate.ModuleSpec spec, @PageableDefault(sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(this.moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable), HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> getModule(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId) {
        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);
        return moduleModelOptional.isEmpty() ? new ResponseEntity<>("Module not found for this course", HttpStatus.NOT_FOUND) : new ResponseEntity<>(moduleModelOptional.get(), HttpStatus.OK);
    }
}
