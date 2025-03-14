package com.brunoandreotti.course.repositories;

import com.brunoandreotti.course.models.LessonModel;
import com.brunoandreotti.course.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonModel, UUID> {

    @Query(value = "select * from tb_lessons where module_id = :moduleId", nativeQuery = true)
    List<LessonModel> findAllLEssonsIntoModule(@Param("moduleId") UUID moduleId);
}
