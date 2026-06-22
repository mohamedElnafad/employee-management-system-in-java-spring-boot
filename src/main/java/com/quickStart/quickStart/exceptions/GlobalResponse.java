package com.quickStart.quickStart.exceptions;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonPropertyOrder({"status", "data", "errors"})
public class GlobalResponse<T> {

    private final static String ERROR = "error";
    private final static String SUCCESS = "success";

    private final String status;
    private final T data;
    private final List<Errors> errors;

    // error case
    public GlobalResponse(List<Errors> errors) {
        this.status = ERROR;
        this.data = null;
        this.errors = errors;
    }

    // success case
    public GlobalResponse(T data) {
        this.status = SUCCESS;
        this.data = data;
        this.errors = null;
    }

    public record Errors(String message) {
    }


}
