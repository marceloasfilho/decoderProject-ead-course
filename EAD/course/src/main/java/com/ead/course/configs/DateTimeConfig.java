package com.ead.course.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.format.DateTimeFormatter;

//@Configuration
public class DateTimeConfig {
    public static final LocalDateTimeSerializer SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

    @Bean
    @Primary
    public ObjectMapper dateTimeMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(SERIALIZER);
        return new ObjectMapper().registerModule(javaTimeModule);
    }
}
