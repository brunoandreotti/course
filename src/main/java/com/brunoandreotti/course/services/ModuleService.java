package com.brunoandreotti.course.services;

import com.brunoandreotti.course.dtos.ModuleRecordDTO;
import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.ModuleModel;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface ModuleService {

    void delete(UUID courseId, UUID moduleId);

    ModuleModel save(ModuleRecordDTO moduleRecordDTO, UUID courseId);

    List<ModuleModel> listAllByCourseId(UUID courseID);

    ModuleModel findModuleIntoCourse(UUID courseId, UUID moduleId);

    ModuleModel update(UUID courseId, UUID moduleId, @Valid ModuleRecordDTO moduleRecordDTO);
}
