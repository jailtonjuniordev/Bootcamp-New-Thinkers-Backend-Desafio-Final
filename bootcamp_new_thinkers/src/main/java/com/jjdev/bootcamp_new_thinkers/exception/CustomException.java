package com.jjdev.bootcamp_new_thinkers.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class CustomException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;
    private List<String> fields;

    public CustomException(String message, HttpStatus httpStatus, List<String> fields) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.fields = fields;
    }
}
