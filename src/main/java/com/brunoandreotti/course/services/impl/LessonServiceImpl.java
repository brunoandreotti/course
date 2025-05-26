package com.brunoandreotti.course.services.impl;


import com.brunoandreotti.course.dtos.LessonRecordDTO;
import com.brunoandreotti.course.models.LessonModel;
import com.brunoandreotti.course.models.ModuleModel;
import com.brunoandreotti.course.repositories.LessonRepository;
import com.brunoandreotti.course.services.LessonService;
import com.brunoandreotti.course.services.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
        LessonModel lesson = new LessonModel();
        BeanUtils.copyProperties(lessonRecordDTO, lesson);
        lesson.setModule(module);
        lesson.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));

        return lessonRepository.save(lesson);
    }
}
