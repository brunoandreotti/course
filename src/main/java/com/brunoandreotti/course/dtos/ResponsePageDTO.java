package com.brunoandreotti.course.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsePageDTO<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ResponsePageDTO(@JsonProperty("content") List<T> content,
                           @JsonProperty("page") PageMetadata page)
    {
        super(content, PageRequest.of(page.getNumber(), page.getSize()), page.getTotalElements());
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class PageMetadata {
        private final Integer size;
        private final Long totalElements;
        private final Integer totalPages;
        private final Integer number;

        @JsonCreator
        public PageMetadata(@JsonProperty("size") Integer size,
                            @JsonProperty("totalElements") Long totalElements,
                            @JsonProperty("totalPages") Integer totalPages,
                            @JsonProperty("number") Integer number
                            ) {
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.number = number;
        }
    }
}
