package com.ead.course.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ResponseGetAllUsersByCourseDTO<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ResponseGetAllUsersByCourseDTO(
            @JsonProperty("content") List<T> content,
            @JsonProperty("number") Integer number,
            @JsonProperty("size") Integer size,
            @JsonProperty("totalElements") Long totalElements,
            @JsonProperty("pageable") JsonNode pageable,
            @JsonProperty("last") Boolean last,
            @JsonProperty("totalPage") Integer totalPage,
            @JsonProperty("sort") JsonNode sort,
            @JsonProperty("first") Boolean first,
            @JsonProperty("empty") Boolean empty
    ) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    public ResponseGetAllUsersByCourseDTO(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ResponseGetAllUsersByCourseDTO(List<T> content) {
        super(content);
    }
}
