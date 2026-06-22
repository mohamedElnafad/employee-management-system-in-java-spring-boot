package com.quickStart.quickStart.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomeExceptionHandler extends RuntimeException {
    private int code;
    private String message;

    // static -> don't create new instance every time, just call the method directly
    public static CustomeExceptionHandler ResourseNotFound(String message) {
        return new CustomeExceptionHandler(400, message);
    }
}
