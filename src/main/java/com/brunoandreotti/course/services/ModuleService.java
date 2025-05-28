package com.brunoandreotti.course.services;

import com.brunoandreotti.course.dtos.ModuleRecordDTO;
import com.brunoandreotti.course.models.ModuleModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface ModuleService {

    void delete(UUID courseId, UUID moduleId);

    ModuleModel save(ModuleRecordDTO moduleRecordDTO, UUID courseId);

    List<ModuleModel> findAllModulesIntoCourse(UUID courseID);

    Page<ModuleModel> findAllModulesIntoCourse(Specification<ModuleModel> spec, Pageable pageable, UUID courseID);

    ModuleModel findModuleIntoCourse(UUID courseId, UUID moduleId);

    ModuleModel update(UUID courseId, UUID moduleId, @Valid ModuleRecordDTO moduleRecordDTO);

    ModuleModel findById(UUID moduleId);
}
