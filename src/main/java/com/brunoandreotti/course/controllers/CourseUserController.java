package com.brunoandreotti.course.controllers;


import com.brunoandreotti.course.clients.UserClient;
import com.brunoandreotti.course.dtos.SubscriptionRecordDTO;
import com.brunoandreotti.course.dtos.UserRecordDTO;
import com.brunoandreotti.course.services.CourseService;
import com.brunoandreotti.course.services.CourseUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CourseUserController {

    private final UserClient userClient;
    private final CourseUserService courseUserService;

    public CourseUserController(UserClient userClient, CourseService courseService, CourseUserService courseUserService) {
        this.userClient = userClient;
        this.courseUserService = courseUserService;
    }

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserRecordDTO>> getAllUsersByCourse(
            @PathVariable(value = "courseId") UUID courseId,
            @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(courseUserService.getAllUsersByCourse(courseId, pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(
            @PathVariable(value = "courseId") UUID userId,
            @RequestBody @Valid SubscriptionRecordDTO subscriptionRecordDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(courseUserService.saveSubscriptionUserInCourse(userId, subscriptionRecordDTO));
    }
}
