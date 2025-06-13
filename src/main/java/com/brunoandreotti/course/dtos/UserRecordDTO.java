package com.brunoandreotti.course.dtos;

import com.brunoandreotti.course.enums.UserStatus;
import com.brunoandreotti.course.enums.UserType;

import java.util.UUID;

public record UserRecordDTO(UUID userId,
                            String username,
                            String email,
                            String fullName,
                            UserStatus userStatus,
                            UserType userType,
                            String phoneNumber,
                            String imageUrl
                            ) {
}
