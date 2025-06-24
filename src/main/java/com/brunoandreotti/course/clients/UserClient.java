package com.brunoandreotti.course.clients;


import com.brunoandreotti.course.dtos.ResponsePageDTO;
import com.brunoandreotti.course.dtos.UserCourseRequestDTO;
import com.brunoandreotti.course.dtos.UserRecordDTO;
import com.brunoandreotti.course.exceptions.ClientErrorException;
import com.brunoandreotti.course.exceptions.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@Log4j2
public class UserClient {

    @Value("${integration.url.user}")
    private String authUserBaseUrl;

    private final RestClient restClient;

    public UserClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public Page<UserRecordDTO> getAllUsersByCourse(UUID userId, Pageable pageable) {

        String url = authUserBaseUrl + String.format("/users?courseId=%s&page=%s&size=%s&sort=%s",

                userId,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().
                        toString().
                        replaceAll(":",",").
                        replaceAll(" ", ""));

        try {
            log.info("GET - getAllUsersByCourse");
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ResponsePageDTO<UserRecordDTO>>() {});
        } catch (RestClientException ex) {
           log.error("Error Request RestClient getAllUsersByCourse with cause: {}", ex.getMessage());
           throw new ClientErrorException("Error Request RestClient getAllUsersByCourse", ex);
        }
    }

    public UserRecordDTO getOneUserById(UUID userId) {
        String url = authUserBaseUrl + String.format("/users/%s", userId);

        log.info("GET - getOneUserById");
        return restClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(),
                        (request, response) -> {
                            log.error("Error Request RestClient getOneUserById with cause: {}", response.getBody().toString());
                            throw new ClientErrorException("Error Request RestClient getOneUserById", response.getStatusCode().value());
                        })
                .toEntity(UserRecordDTO.class).getBody();
    }

    public void postSubscriptionUserInCourse(UUID userId, UUID courseId) {
        String url = authUserBaseUrl + String.format("/users/%s/courses/subscription", userId);

        UserCourseRequestDTO userCourseRequest = new UserCourseRequestDTO(courseId);

        log.info("POST - postSubscriptionUserInCourse");
        restClient.post()
                .uri(url)
                .body(userCourseRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(),
                        (request, response) -> {
                            log.error("Error Request RestClient postSubscriptionUserInCourse with cause: {}", response.getBody().toString());
                            throw new ClientErrorException("Error Request RestClient postSubscriptionUserInCourse", response.getStatusCode().value());
                        })
                .toBodilessEntity();
    }

    public void deleteCourseUserInAuthUser(UUID courseId) {
        String url = authUserBaseUrl + String.format("/users/courses/%s", courseId);

        log.info("DELETE - deleteCourseUserInAuthUser");
        restClient.delete()
                .uri(url)
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(),
                        (request, response) -> {
                            log.error("Error Request deleteCourseUserInAuthUser with cause: {}", response.getBody().toString());
                            throw new ClientErrorException("Error Request RestClient deleteCourseUserInAuthUser", response.getStatusCode().value());
                        })
                .toBodilessEntity();
    }


}
