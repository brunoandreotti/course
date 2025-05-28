package com.brunoandreotti.course.dtos;

import com.brunoandreotti.course.enums.CourseLevel;
import com.brunoandreotti.course.enums.CourseStatus;
import com.brunoandreotti.course.utils.StringUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CourseRecordDTO(@NotBlank(message = StringUtils.NAME_BLANK_ERROR)
                              String name,
                              @NotBlank(message = StringUtils.DESCRIPTION_BLANK_ERROR)
                              String description,
                              @NotNull(message = StringUtils.COURSE_STATUS_BLANK_ERROR)
                              CourseStatus courseStatus,
                              @NotNull(message = StringUtils.COURSE_LEVEL_BLANK_ERROR)
                              CourseLevel courseLevel,
                              @NotNull(message = StringUtils.USER_INSTRUCTOR_BLANK_ERROR)
                              UUID userInstructor,

                              String imageUrl) {

}
