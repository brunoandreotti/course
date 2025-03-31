package com.brunoandreotti.course.controllers;

import com.brunoandreotti.course.dtos.CourseRecordDTO;
import com.brunoandreotti.course.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseRecordDTO courseRecordDTO) {
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseRecordDTO));
    }
}
