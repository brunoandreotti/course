package com.brunoandreotti.course.services.impl;


import com.brunoandreotti.course.repositories.LessonRepository;
import com.brunoandreotti.course.services.LessonService;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

    final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }
}
