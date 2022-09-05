package com.encrypter.enc.dto;

import com.encrypter.enc.pojo.ErrorObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Base Response DTO which can be used in all controllers to send back response
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    @JsonProperty("errors")
    private List<ErrorObject> errors;

    public ResponseDTO(String message, T data, List<ErrorObject> errors) {
        this.message = message;
        this.data = data;
        this.errors = Collections.unmodifiableList(errors);
    }

    public ResponseDTO(String message, List<ErrorObject> errors) {
        this.message = message;
        this.errors = Collections.unmodifiableList(errors);
    }

    public ResponseDTO(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(String message, Exception exception) {
        this.message = message;
        ErrorObject errorObject = new ErrorObject(exception.getClass().getSimpleName(), exception.getMessage());
        this.errors = new ArrayList<>(Collections.singletonList(errorObject));
    }

    public ResponseDTO(String message) {
        this.message = message;
        this.status = Boolean.TRUE;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
