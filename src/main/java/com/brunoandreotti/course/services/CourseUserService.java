package com.brunoandreotti.course.services;

import com.brunoandreotti.course.dtos.CourseRecordDTO;
import com.brunoandreotti.course.dtos.SubscriptionRecordDTO;
import com.brunoandreotti.course.dtos.UserRecordDTO;
import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.CourseUserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseUserService {
    CourseUserModel saveSubscriptionUserInCourse(UUID courseId, SubscriptionRecordDTO subscriptionRecordDTO);

    Page<UserRecordDTO> getAllUsersByCourse(UUID courseId, Pageable pageable);

    void deleteCourseUserByUser(UUID userId);
}
