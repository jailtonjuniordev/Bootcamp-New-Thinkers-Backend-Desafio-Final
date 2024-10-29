package com.jjdev.bootcamp_new_thinkers.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RestExceptionTemplate {
    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime errorTime;
    private List<String> fields;
}
