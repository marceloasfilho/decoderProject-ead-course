package com.github.marceloasfilho.eadCourse.service;

import com.github.marceloasfilho.eadCourse.model.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    LessonModel save(LessonModel lesson);

    Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(LessonModel lessonModel);

    Page<LessonModel> findAllByModule(Specification<LessonModel> spec, Pageable pageable);
}
