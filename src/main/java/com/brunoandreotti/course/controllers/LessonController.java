package com.brunoandreotti.course.controllers;

import com.brunoandreotti.course.dtos.LessonRecordDTO;
import com.brunoandreotti.course.models.LessonModel;
import com.brunoandreotti.course.services.LessonService;
import com.brunoandreotti.course.specifications.SpecificationTemplate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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


    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Page<LessonModel>> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId,
                                                           SpecificationTemplate.LessonSpec spec,
                                                           Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllLessonsIntoModule(spec, pageable, moduleId));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<LessonModel> findLessonById(@PathVariable(value = "moduleId") UUID moduleId,
                                                      @PathVariable(value = "lessonId") UUID lessonId) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findLessonIntoModule(moduleId, lessonId));
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLessonById(@PathVariable(value = "moduleId") UUID moduleId,
                                                   @PathVariable(value = "lessonId") UUID lessonId) {
        lessonService.delete(moduleId, lessonId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<LessonModel> updateLessonById(@PathVariable(value = "moduleId") UUID moduleId,
                                                        @PathVariable(value = "lessonId") UUID lessonId,
                                                        @RequestBody @Valid LessonRecordDTO lessonRecordDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(lessonService.update(moduleId, lessonId, lessonRecordDTO));
    }
}
