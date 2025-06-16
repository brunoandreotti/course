package com.brunoandreotti.course.services;

import com.brunoandreotti.course.dtos.SubscriptionRecordDTO;
import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {
    CourseUserModel saveSubscriptionUserInCourse(UUID courseId,
                                      SubscriptionRecordDTO subscriptionRecordDTO);
    Boolean checkIfSubscriptionExists (CourseModel course, UUID userId);
    CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel);
}
