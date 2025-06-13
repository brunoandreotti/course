package com.brunoandreotti.course.clients;


import com.brunoandreotti.course.dtos.ResponsePageDTO;
import com.brunoandreotti.course.dtos.UserRecordDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Component
@Log4j2
public class UserClient {

    @Value("${integration.url.user}")
    private String courseBaseUrl;

    private final RestClient restClient;

    public UserClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public Page<UserRecordDTO> getAllUsersByCourse(UUID userId, Pageable pageable) {

        String url = courseBaseUrl + String.format("/users?courseId=%s&page=%s&size=%s&sort=%s",

                userId,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().
                        toString().
                        replaceAll(":",",").
                        replaceAll(" ", ""));

        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ResponsePageDTO<UserRecordDTO>>() {});
        } catch (RestClientException ex) {
           log.error("Error Request RestClient with cause: {}", ex.getMessage());
           throw new RuntimeException("Error Request RestClient", ex);
        }
    }
}
