package com.brunoandreotti.course.services;

import com.brunoandreotti.course.dtos.CourseRecordDTO;
import com.brunoandreotti.course.models.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public interface CourseService {

    void delete(CourseModel courseModel);

    CourseModel save(CourseRecordDTO courseRecordDTO);

    Page<CourseModel> listAll(Specification<CourseModel> spec, Pageable pageable);

    CourseModel findById(UUID courseId);

    CourseModel update(UUID courseId, CourseRecordDTO courseRecordDTO);
}
