package com.brunoandreotti.course.services.impl;


import com.brunoandreotti.course.dtos.LessonRecordDTO;
import com.brunoandreotti.course.exceptions.AlreadyExistsException;
import com.brunoandreotti.course.exceptions.NotFoundException;
import com.brunoandreotti.course.models.LessonModel;
import com.brunoandreotti.course.models.ModuleModel;
import com.brunoandreotti.course.repositories.LessonRepository;
import com.brunoandreotti.course.services.LessonService;
import com.brunoandreotti.course.services.ModuleService;
import com.brunoandreotti.course.specifications.SpecificationTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.management.RuntimeMBeanException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;

    public LessonServiceImpl(LessonRepository lessonRepository, ModuleService moduleService) {
        this.lessonRepository = lessonRepository;
        this.moduleService = moduleService;
    }

    @Override
    public LessonModel save(LessonRecordDTO lessonRecordDTO, UUID moduleId) {
        ModuleModel module = moduleService.findById(moduleId);

        if (lessonRepository.existsByTitle(lessonRecordDTO.title())) {
            throw new AlreadyExistsException("Lesson with this title already exists");
        }

        LessonModel lesson = new LessonModel();
        BeanUtils.copyProperties(lessonRecordDTO, lesson);
        lesson.setModule(module);
        lesson.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return lessonRepository.save(lesson);
    }

    @Override
    public List<LessonModel> findAllLessonsIntoModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }

    @Override
    public LessonModel findLessonIntoModule(UUID moduleId, UUID lessonId) {
        Optional<LessonModel> lesson = lessonRepository.findLessonIntoModule(moduleId, lessonId);

        if (lesson.isEmpty()) {
            throw new NotFoundException("Lesson not found");
        }

        return lesson.get();
    }

    @Override
    public Page<LessonModel> findAllLessonsIntoModule(Specification<LessonModel> spec, Pageable pageable, UUID moduleId) {

        Specification<LessonModel> specWithModuleId = SpecificationTemplate.lessonModuleId(moduleId).and(spec);
        return lessonRepository.findAll(specWithModuleId, pageable);
    }

    @Override
    public void delete(UUID moduleId, UUID lessonId) {
       LessonModel lesson = findLessonIntoModule(moduleId, lessonId);
        lessonRepository.delete(lesson);
    }

    @Override
    public LessonModel update(UUID moduleId, UUID lessonId, LessonRecordDTO lessonRecordDTO) {
        LessonModel lessonModel = findLessonIntoModule(moduleId, lessonId);

        BeanUtils.copyProperties(lessonRecordDTO, lessonModel);
        return lessonRepository.save(lessonModel);
    }




}
