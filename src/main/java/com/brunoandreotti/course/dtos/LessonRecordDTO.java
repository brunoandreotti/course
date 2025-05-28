package com.brunoandreotti.course.dtos;

import com.brunoandreotti.course.utils.StringUtils;
import jakarta.validation.constraints.NotBlank;

public record LessonRecordDTO(@NotBlank(message = StringUtils.TITLE_BLANK_ERROR)
                              String title,
                              @NotBlank(message = StringUtils.DESCRIPTION_BLANK_ERROR)
                              String description,
                              @NotBlank(message = StringUtils.VIDEO_URL_BLANK_ERROR)
                              String videoUrl
                              ) {
}
