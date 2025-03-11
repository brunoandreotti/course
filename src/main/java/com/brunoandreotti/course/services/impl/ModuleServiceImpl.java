package com.brunoandreotti.course.services.impl;

import com.brunoandreotti.course.repositories.ModuleRepository;
import com.brunoandreotti.course.services.ModuleService;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {

    final ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }
}
