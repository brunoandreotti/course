package com.brunoandreotti.course.services.impl;

import com.brunoandreotti.course.clients.UserClient;
import com.brunoandreotti.course.dtos.SubscriptionRecordDTO;
import com.brunoandreotti.course.dtos.UserRecordDTO;
import com.brunoandreotti.course.enums.UserStatus;
import com.brunoandreotti.course.exceptions.BlockedStatusException;
import com.brunoandreotti.course.exceptions.AlreadyExistsException;
import com.brunoandreotti.course.exceptions.NotFoundException;
import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.CourseUserModel;
import com.brunoandreotti.course.repositories.CourseRepository;
import com.brunoandreotti.course.repositories.CourseUserRepository;
import com.brunoandreotti.course.services.CourseService;
import com.brunoandreotti.course.services.CourseUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseService courseService;
    private final CourseUserRepository courseUserRepository;
    private final UserClient userClient;


    public CourseUserServiceImpl(CourseService courseService, CourseUserRepository courseUserRepository, UserClient userClient) {
        this.courseService = courseService;
        this.courseUserRepository = courseUserRepository;
        this.userClient = userClient;

    }

    @Transactional
    @Override
    public CourseUserModel saveSubscriptionUserInCourse(UUID courseId,
                                                        SubscriptionRecordDTO subscriptionRecordDTO) {
        CourseModel course = courseService.findById(courseId);
        UUID userId = subscriptionRecordDTO.userId();

        if (checkIfSubscriptionExists(course, userId)) {
           throw new AlreadyExistsException("User: " + userId + " already subscribed in course " + course.getCourseId());
        }

        UserRecordDTO user = userClient.getOneUserById(userId);

        if (user.userStatus().equals(UserStatus.BLOCKED)) {
            throw new BlockedStatusException("User is blocked!");
        }


        return saveAndSendSubscriptionUserInCourse(course.toCourseUserModel(userId));
    }

    @Override
    public Page<UserRecordDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        CourseModel course = courseService.findById(courseId);

        return userClient.getAllUsersByCourse(course.getCourseId(), pageable);
    }

    @Transactional
    @Override
    public void deleteCourseUserByUser(UUID userId) {
            Boolean existsByUserId = courseUserRepository.existsByUserId(userId);

            if (!existsByUserId) {
                throw new NotFoundException("Result with userId " + userId + " not found");
            }

        courseUserRepository.deleteAllByUserId(userId);
    }

    private Boolean checkIfSubscriptionExists (CourseModel course, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(course, userId);

    }

    private CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel) {
        CourseUserModel savedCourseUserModel = courseUserRepository.save(courseUserModel);

        userClient.postSubscriptionUserInCourse(courseUserModel.getUserId(), courseUserModel.getCourse().getCourseId());

        return savedCourseUserModel;
    }
}
