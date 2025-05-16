package com.brunoandreotti.course.controllers;

import com.brunoandreotti.course.dtos.ModuleRecordDTO;
import com.brunoandreotti.course.services.ModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
