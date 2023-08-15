package com.github.marceloasfilho.eadCourse.service.impl;

import com.github.marceloasfilho.eadCourse.service.CourseService;
import com.github.marceloasfilho.eadCourse.model.CourseModel;
import com.github.marceloasfilho.eadCourse.model.LessonModel;
import com.github.marceloasfilho.eadCourse.model.ModuleModel;
import com.github.marceloasfilho.eadCourse.repository.CourseRepository;
import com.github.marceloasfilho.eadCourse.repository.LessonRepository;
import com.github.marceloasfilho.eadCourse.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;


    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        List<ModuleModel> allModulesIntoCourse = this.moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());
        if (!allModulesIntoCourse.isEmpty()) {
            for (ModuleModel module : allModulesIntoCourse) {
                List<LessonModel> allLessonsIntoModule = this.lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!allLessonsIntoModule.isEmpty()) {
                    this.lessonRepository.deleteAll(allLessonsIntoModule);
                }
            }
            this.moduleRepository.deleteAll(allModulesIntoCourse);
        }
        this.courseRepository.delete(courseModel);
    }

    @Override
    public CourseModel save(CourseModel courseModel) {
        return this.courseRepository.save(courseModel);
    }

    @Transactional
    @Override
    public Optional<CourseModel> findById(UUID courseId) {
        return this.courseRepository.findById(courseId);
    }

    @Override
    public Page<CourseModel> findAll(Specification<CourseModel> spec, Pageable pageable) {
        return this.courseRepository.findAll(spec, pageable);
    }
}
