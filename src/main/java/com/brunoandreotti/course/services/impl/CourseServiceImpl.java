package com.brunoandreotti.course.services.impl;

import com.brunoandreotti.course.dtos.CourseRecordDTO;
import com.brunoandreotti.course.models.CourseModel;
import com.brunoandreotti.course.models.LessonModel;
import com.brunoandreotti.course.models.ModuleModel;
import com.brunoandreotti.course.repositories.CourseRepository;
import com.brunoandreotti.course.repositories.LessonRepository;
import com.brunoandreotti.course.repositories.ModuleRepository;
import com.brunoandreotti.course.services.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    final CourseRepository courseRepository;
    final ModuleRepository moduleRepository;
    final LessonRepository lessonRepository;

    public CourseServiceImpl(CourseRepository courseRepository, ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void delete(CourseModel courseModel) {
        List<ModuleModel> moduleList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());

        if (!moduleList.isEmpty()) {

            for (ModuleModel module : moduleList ) {
                List<LessonModel> lessonList = lessonRepository.findAllLEssonsIntoModule(module.getModuleId());

                if (!lessonList.isEmpty()) {
                    lessonRepository.deleteAll(lessonList);
                }
            }

            moduleRepository.deleteAll(courseModel.getModules());
        }
        courseRepository.delete(courseModel);
    }

    @Override
    public CourseModel save(CourseRecordDTO courseRecordDTO) {

        if (courseRepository.existsByName(courseRecordDTO.name())) {
            throw new RuntimeException("Course with this name already exists");
        }

        var courseModel = new CourseModel().fromDTO(courseRecordDTO);
        courseModel.setCreatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(courseModel);
    }

    @Override
    public List<CourseModel> listAll() {
        return courseRepository.findAll();
    }

    @Override
    public CourseModel findById(UUID courseId) {
        Optional<CourseModel> course = courseRepository.findById(courseId);

        if (course.isEmpty()) {
            throw new RuntimeException("Curso não encontrado");
        }

        return course.get();
    }

    @Override
    public CourseModel update(UUID courseId, CourseRecordDTO courseRecordDTO) {
        CourseModel courseModel = findById(courseId);

        BeanUtils.copyProperties(courseRecordDTO, courseModel);
        courseModel.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(courseModel);
    }


}
