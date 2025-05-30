package com.brunoandreotti.course.controllers;

import com.brunoandreotti.course.dtos.ModuleRecordDTO;
import com.brunoandreotti.course.models.ModuleModel;
import com.brunoandreotti.course.services.ModuleService;
import com.brunoandreotti.course.specifications.SpecificationTemplate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ModuleController {

    final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(@PathVariable(value = "courseId") UUID courseId,
                                             @RequestBody @Valid ModuleRecordDTO moduleRecordDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(moduleRecordDTO, courseId));
    }

    @GetMapping("/courses/{courseId}/modules")
    public ResponseEntity<Page<ModuleModel>> getAllModules(@PathVariable(value = "courseId") UUID courseId,
                                                           SpecificationTemplate.ModuleSpec spec,
                                                           Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.findAllModulesIntoCourse(spec, pageable, courseId));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<ModuleModel> findModuleById(@PathVariable(value = "courseId") UUID courseId,
                                                      @PathVariable(value = "moduleId") UUID moduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findModuleIntoCourse(courseId, moduleId));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModuleById(@PathVariable(value = "courseId") UUID courseId,
                                                   @PathVariable(value = "moduleId") UUID moduleId) {
        moduleService.delete(courseId, moduleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<ModuleModel> updateModuleById(@PathVariable(value = "courseId") UUID courseId,
                                                   @PathVariable(value = "moduleId") UUID moduleId,
                                                   @RequestBody @Valid ModuleRecordDTO moduleRecordDTO) {

        return ResponseEntity.status(HttpStatus.OK).body(moduleService.update(courseId, moduleId, moduleRecordDTO));
    }
}
