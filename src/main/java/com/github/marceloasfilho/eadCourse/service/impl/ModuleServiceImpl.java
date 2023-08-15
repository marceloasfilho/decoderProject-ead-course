package com.github.marceloasfilho.eadCourse.service.impl;

import com.github.marceloasfilho.eadCourse.service.ModuleService;
import com.github.marceloasfilho.eadCourse.model.LessonModel;
import com.github.marceloasfilho.eadCourse.model.ModuleModel;
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
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(ModuleModel moduleModel) {
        List<LessonModel> allLessonsIntoModule = this.lessonRepository.findAllLessonsIntoModule(moduleModel.getModuleId());
        if (!allLessonsIntoModule.isEmpty()) {
            this.lessonRepository.deleteAll(allLessonsIntoModule);
        }
        this.moduleRepository.delete(moduleModel);
    }

    @Override
    public ModuleModel save(ModuleModel module) {
        return this.moduleRepository.save(module);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return this.moduleRepository.findModuleIntoCourse(courseId, moduleId);
    }

    @Override
    public List<ModuleModel> findAllByCourse(UUID courseId) {
        return this.moduleRepository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public Optional<ModuleModel> findById(UUID moduleId) {
        return this.moduleRepository.findById(moduleId);
    }

    @Override
    public Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable) {
        return this.moduleRepository.findAll(spec, pageable);
    }
}
