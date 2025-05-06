package com.brunoandreotti.course.services;

import com.brunoandreotti.course.dtos.CourseRecordDTO;
import com.brunoandreotti.course.models.CourseModel;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    void delete(CourseModel courseModel);

    CourseModel save(CourseRecordDTO courseRecordDTO);

    List<CourseModel> listAll();

    CourseModel findById(UUID courseId);

    CourseModel update(UUID courseId, CourseRecordDTO courseRecordDTO);
}
