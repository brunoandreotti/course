package com.brunoandreotti.course.controllers;

import com.brunoandreotti.course.dtos.LessonRecordDTO;
import com.brunoandreotti.course.services.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                             @RequestBody @Valid LessonRecordDTO lessonRecordDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonRecordDTO, moduleId));
    }
}
