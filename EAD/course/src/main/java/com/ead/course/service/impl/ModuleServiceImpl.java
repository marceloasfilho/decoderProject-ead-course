package com.ead.course.service.impl;

import com.ead.course.model.LessonModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
}
