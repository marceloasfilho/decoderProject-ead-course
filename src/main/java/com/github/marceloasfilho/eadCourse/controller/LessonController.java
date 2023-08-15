package com.github.marceloasfilho.eadCourse.controller;

import com.github.marceloasfilho.eadCourse.dto.LessonDTO;
import com.github.marceloasfilho.eadCourse.model.LessonModel;
import com.github.marceloasfilho.eadCourse.model.ModuleModel;
import com.github.marceloasfilho.eadCourse.service.LessonService;
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
public class LessonController {
    private final LessonService lessonService;
    private final ModuleService moduleService;

    @PostMapping("/module/{moduleId}/lessons")
    public ResponseEntity<?> saveLesson(@PathVariable(value = "moduleId") UUID moduleId, @RequestBody @Valid LessonDTO lessonDTO) {

        Optional<ModuleModel> moduleModelOptional = this.moduleService.findById(moduleId);
        if (moduleModelOptional.isEmpty()) {
            return new ResponseEntity<>("Module not found", HttpStatus.NOT_FOUND);
        }

        var lesson = new LessonModel();
        BeanUtils.copyProperties(lessonDTO, lesson);

        lesson.setCreationDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        lesson.setModule(moduleModelOptional.get());

        return new ResponseEntity<>(this.lessonService.save(lesson), HttpStatus.CREATED);
    }

    @DeleteMapping("/module/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<?> deleteLesson(@PathVariable(value = "moduleId") UUID moduleId, @PathVariable(value = "lessonId") UUID lessonId) {

        Optional<LessonModel> lessonModelOptional = this.lessonService.findLessonIntoModule(moduleId, lessonId);

        if (lessonModelOptional.isEmpty()) {
            return new ResponseEntity<>("Lesson not found for this module", HttpStatus.NOT_FOUND);
        }

        this.lessonService.delete(lessonModelOptional.get());
        return new ResponseEntity<>("Lesson deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/module/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<?> updateLesson(@PathVariable(value = "moduleId") UUID moduleId, @PathVariable(value = "lessonId") UUID lessonId, @RequestBody @Valid LessonDTO lessonDTO) {

        Optional<LessonModel> lessonModelOptional = this.lessonService.findLessonIntoModule(moduleId, lessonId);

        if (lessonModelOptional.isEmpty()) {
            return new ResponseEntity<>("Lesson not found for this module", HttpStatus.NOT_FOUND);
        }

        var lesson = lessonModelOptional.get();
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setVideoUrl(lessonDTO.getVideoUrl());

        return new ResponseEntity<>(this.lessonService.save(lesson), HttpStatus.OK);
    }

    @GetMapping("/module/{moduleId}/lessons")
    public ResponseEntity<Page<LessonModel>> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId,
                                                           SpecificationTemplate.LessonSpec spec, @PageableDefault(sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(this.lessonService.findAllByModule(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable), HttpStatus.OK);
    }

    @GetMapping("/module/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<?> getLesson(@PathVariable(value = "moduleId") UUID moduleId, @PathVariable(value = "lessonId") UUID lessonId) {
        Optional<LessonModel> lessonModelOptional = this.lessonService.findLessonIntoModule(moduleId, lessonId);
        return lessonModelOptional.isEmpty() ? new ResponseEntity<>("Lesson not found for this module", HttpStatus.NOT_FOUND) : new ResponseEntity<>(lessonModelOptional.get(), HttpStatus.OK);
    }
}
