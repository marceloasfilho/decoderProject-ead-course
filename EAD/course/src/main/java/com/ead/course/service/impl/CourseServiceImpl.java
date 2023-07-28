package com.ead.course.service.impl;

import com.ead.course.model.CourseModel;
import com.ead.course.model.LessonModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
}
