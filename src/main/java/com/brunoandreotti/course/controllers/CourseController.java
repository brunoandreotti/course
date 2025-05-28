package com.brunoandreotti.course.controllers;

import com.brunoandreotti.course.dtos.CourseRecordDTO;
import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.services.CourseService;
import com.brunoandreotti.course.specifications.SpecificationTemplate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    
    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.listAll(spec, pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseModel> findCourseById(@PathVariable(value = "courseId") UUID courseId) {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.findById(courseId));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourseById(@PathVariable(value = "courseId") UUID courseId) {
        courseService.delete(courseService.findById(courseId));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourseById(@PathVariable(value = "courseId") UUID courseId,
                                                   @RequestBody @Valid CourseRecordDTO courseRecordDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(courseService.update(courseId, courseRecordDTO));
    }


}
