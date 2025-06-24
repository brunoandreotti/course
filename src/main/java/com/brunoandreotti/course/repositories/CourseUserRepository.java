package com.brunoandreotti.course.repositories;

import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID> {

    Boolean existsByCourseAndUserId(CourseModel course, UUID userId);

    @Query(value = "select * from tb_courses_users where course_id = :courseId", nativeQuery = true)
    List<CourseUserModel> findAllCourseUserIntoCourse(@Param("courseId")UUID courseId);

    Boolean existsByUserId(UUID userId);

    void deleteAllByUserId(UUID userId);
}
