package com.brunoandreotti.course.services;

import com.brunoandreotti.course.dtos.LessonRecordDTO;
import com.brunoandreotti.course.models.LessonModel;
import jakarta.validation.Valid;

import java.util.UUID;

public interface LessonService {
    LessonModel save(@Valid LessonRecordDTO lessonRecordDTO, UUID moduleId);
}
