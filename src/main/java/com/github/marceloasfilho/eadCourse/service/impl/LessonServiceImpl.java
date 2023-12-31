package com.github.marceloasfilho.eadCourse.service.impl;

import com.github.marceloasfilho.eadCourse.service.LessonService;
import com.github.marceloasfilho.eadCourse.model.LessonModel;
import com.github.marceloasfilho.eadCourse.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Override
    public LessonModel save(LessonModel lesson) {
        return this.lessonRepository.save(lesson);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return this.lessonRepository.findLessonIntoModule(moduleId, lessonId);
    }

    @Override
    public void delete(LessonModel lessonModel) {
        this.lessonRepository.delete(lessonModel);
    }

    @Override
    public Page<LessonModel> findAllByModule(Specification<LessonModel> spec, Pageable pageable) {
        return this.lessonRepository.findAll(spec, pageable);
    }
}
