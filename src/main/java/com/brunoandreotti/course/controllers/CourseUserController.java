package com.brunoandreotti.course.controllers;


import com.brunoandreotti.course.clients.UserClient;
import com.brunoandreotti.course.dtos.UserRecordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CourseUserController {

    private final UserClient userClient;

    public CourseUserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserRecordDTO>> getAllUsersByCourse(
            @PathVariable(value = "courseId") UUID userId,
            @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllUsersByCourse(userId, pageable));
    }
}
