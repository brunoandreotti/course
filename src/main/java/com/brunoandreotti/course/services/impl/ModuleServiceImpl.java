package com.brunoandreotti.course.services.impl;

import com.brunoandreotti.course.dtos.CourseRecordDTO;
import com.brunoandreotti.course.dtos.ModuleRecordDTO;
import com.brunoandreotti.course.exceptions.NotFoundException;
import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.LessonModel;
import com.brunoandreotti.course.models.ModuleModel;
import com.brunoandreotti.course.repositories.CourseRepository;
import com.brunoandreotti.course.repositories.LessonRepository;
import com.brunoandreotti.course.repositories.ModuleRepository;
import com.brunoandreotti.course.services.CourseService;
import com.brunoandreotti.course.services.ModuleService;
import com.brunoandreotti.course.specifications.SpecificationTemplate;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final CourseService courseService;


    public ModuleServiceImpl(ModuleRepository moduleRepository, LessonRepository lessonRepository, CourseService courseService, CourseRepository courseRepository) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.courseService = courseService;

    }

    @Transactional
    @Override
    public void delete(UUID courseId, UUID moduleId) {

        ModuleModel module = findModuleIntoCourse(courseId, moduleId);

        List<LessonModel> lessonList = lessonRepository.findAllLessonsIntoModule(moduleId);

        if (!lessonList.isEmpty()) {
            lessonRepository.deleteAll(lessonList);
        }

        moduleRepository.delete(module);
    }

    @Override
    public ModuleModel save(ModuleRecordDTO moduleRecordDTO, UUID courseId) {
        CourseModel course = courseService.findById(courseId);

        ModuleModel moduleModel = new ModuleModel().fromDTO(moduleRecordDTO);

        moduleModel.setCourse(course);
        moduleModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return moduleRepository.save(moduleModel);

    }

    @Override
    public List<ModuleModel> findAllModulesIntoCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public Page<ModuleModel> findAllModulesIntoCourse(Specification<ModuleModel> spec, Pageable pageable, UUID courseID) {
        Specification<ModuleModel> specWithCourseId = SpecificationTemplate.moduleCourseId(courseID).and(spec);
        return moduleRepository.findAll(specWithCourseId, pageable);
    }

    @Override
    public ModuleModel findModuleIntoCourse(UUID courseId, UUID moduleId) {
        Optional<ModuleModel> module = moduleRepository.findModuleIntoCourse(courseId, moduleId);

        if (module.isEmpty()) {
            throw new NotFoundException("Module not found");
        }

        return module.get();
    }

    @Override
    public ModuleModel update(UUID courseId, UUID moduleId, ModuleRecordDTO moduleRecordDTO) {
        ModuleModel moduleModel = findModuleIntoCourse(courseId, moduleId);

        BeanUtils.copyProperties(moduleRecordDTO, moduleModel);
        return moduleRepository.save(moduleModel);
    }

    @Override
    public ModuleModel findById(UUID moduleId) {
        Optional<ModuleModel> module = moduleRepository.findById(moduleId);

        if (module.isEmpty()) {
            throw new NotFoundException("Module not found");
        }

        return module.get();
    }


}
