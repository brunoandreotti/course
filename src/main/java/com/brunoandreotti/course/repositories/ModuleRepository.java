package com.brunoandreotti.course.repositories;

import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {
}
