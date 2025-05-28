package com.brunoandreotti.course.services;

import com.brunoandreotti.course.dtos.LessonRecordDTO;
import com.brunoandreotti.course.models.LessonModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface LessonService {
    LessonModel save(@Valid LessonRecordDTO lessonRecordDTO, UUID moduleId);

    List<LessonModel> findAllLessonsIntoModule(UUID moduleId);

    Page<LessonModel> findAllLessonsIntoModule(Specification<LessonModel> spec, Pageable pageable, UUID moduleId);

    LessonModel findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(UUID moduleId, UUID lessonId);

   LessonModel update(UUID moduleId, UUID lessonId, @Valid LessonRecordDTO lessonRecordDTO);


}
