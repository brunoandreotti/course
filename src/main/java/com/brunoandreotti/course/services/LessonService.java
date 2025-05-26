package com.brunoandreotti.course.services;

import com.brunoandreotti.course.dtos.LessonRecordDTO;
import com.brunoandreotti.course.models.LessonModel;
import com.brunoandreotti.course.models.ModuleModel;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    LessonModel save(@Valid LessonRecordDTO lessonRecordDTO, UUID moduleId);

    List<LessonModel> listAllByModuleId(UUID moduleId);

    LessonModel findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(UUID moduleId, UUID lessonId);

   LessonModel update(UUID moduleId, UUID lessonId, @Valid LessonRecordDTO lessonRecordDTO);
}
