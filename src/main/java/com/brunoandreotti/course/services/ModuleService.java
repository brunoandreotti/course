package com.brunoandreotti.course.services;

import com.brunoandreotti.course.dtos.ModuleRecordDTO;
import com.brunoandreotti.course.models.ModuleModel;

import java.util.UUID;

public interface ModuleService {

    void delete(ModuleModel moduleModel);

    ModuleModel save(ModuleRecordDTO moduleRecordDTO, UUID courseId);
}
