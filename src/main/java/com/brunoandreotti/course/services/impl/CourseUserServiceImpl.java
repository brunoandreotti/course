package com.brunoandreotti.course.services.impl;

import com.brunoandreotti.course.dtos.SubscriptionRecordDTO;
import com.brunoandreotti.course.exceptions.SubscriptionAlreadyExistsException;
import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.CourseUserModel;
import com.brunoandreotti.course.repositories.CourseUserRepository;
import com.brunoandreotti.course.services.CourseService;
import com.brunoandreotti.course.services.CourseUserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseService courseService;
    private final CourseUserRepository courseUserRepository;

    public CourseUserServiceImpl(CourseService courseService, CourseUserRepository courseUserRepository) {
        this.courseService = courseService;
        this.courseUserRepository = courseUserRepository;
    }

    @Override
    public CourseUserModel saveSubscriptionUserInCourse(UUID courseId,
                                                        SubscriptionRecordDTO subscriptionRecordDTO) {
        CourseModel course = courseService.findById(courseId);
        UUID userId = subscriptionRecordDTO.userId();

        if (checkIfSubscriptionExists(course, userId)) {
           throw new SubscriptionAlreadyExistsException("User: " + userId + " already subscribed in course " + course.getCourseId());
        }

        //TODO: user verification


        return saveAndSendSubscriptionUserInCourse(course.toCourseUserModel(userId));
    }

    @Override
    public Boolean checkIfSubscriptionExists (CourseModel course, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(course, userId);

    }

    @Override
    public CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel) {
        CourseUserModel savedCourseUserModel = courseUserRepository.save(courseUserModel);

        //TODO: send user and course to authUser microservice

        return savedCourseUserModel;
    }
}
