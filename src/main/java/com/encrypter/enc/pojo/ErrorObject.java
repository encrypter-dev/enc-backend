package com.encrypter.enc.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The base error object to be used in all response DTOs
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ErrorObject {

    private String type;

    private String message;

    private Integer code;

    public ErrorObject(String message) {
        this.message = message;
    }

    public ErrorObject(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public ErrorObject(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
