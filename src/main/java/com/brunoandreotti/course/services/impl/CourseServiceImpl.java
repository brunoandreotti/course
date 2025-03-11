package com.brunoandreotti.course.services.impl;

import com.brunoandreotti.course.repositories.CourseRepository;
import com.brunoandreotti.course.services.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;    }
}
