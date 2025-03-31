package com.brunoandreotti.course.dtos;

import com.brunoandreotti.course.enums.CourseLevel;
import com.brunoandreotti.course.enums.CourseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CourseRecordDTO(@NotBlank
                              String name,
                              @NotBlank
                              String description,
                              @NotNull
                              CourseStatus courseStatus,
                              @NotNull
                              CourseLevel courseLevel,
                              @NotNull
                              UUID userInstructor,

                              String imageUrl) {

}
