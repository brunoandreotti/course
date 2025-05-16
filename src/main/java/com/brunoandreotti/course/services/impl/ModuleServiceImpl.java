package com.brunoandreotti.course.services.impl;

import com.brunoandreotti.course.dtos.ModuleRecordDTO;
import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.LessonModel;
import com.brunoandreotti.course.models.ModuleModel;
import com.brunoandreotti.course.repositories.LessonRepository;
import com.brunoandreotti.course.repositories.ModuleRepository;
import com.brunoandreotti.course.services.CourseService;
import com.brunoandreotti.course.services.ModuleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    final ModuleRepository moduleRepository;
    final LessonRepository lessonRepository;
    final CourseService courseService;

    public ModuleServiceImpl(ModuleRepository moduleRepository, LessonRepository lessonRepository, CourseService courseService) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.courseService = courseService;
    }

    @Transactional
    @Override
    public void delete(ModuleModel moduleModel) {

        List<LessonModel> lessonList = lessonRepository.findAllLEssonsIntoModule(moduleModel.getModuleId());

        if (!lessonList.isEmpty()) {
            lessonRepository.deleteAll(lessonList);
        }

        moduleRepository.delete(moduleModel);
    }

    @Override
    public ModuleModel save(ModuleRecordDTO moduleRecordDTO, UUID courseId) {
        CourseModel course = courseService.findById(courseId);

        ModuleModel moduleModel = new ModuleModel().fromDTO(moduleRecordDTO);

        moduleModel.setCourse(course);
        moduleModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return moduleRepository.save(moduleModel);

    }


}
