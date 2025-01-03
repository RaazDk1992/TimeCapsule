package com.raazdk.TimeCapsule.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@NoArgsConstructor
public class AppResponse {
    public AppResponse(HttpStatus statusCode, String successMessage, String errorMessage) {
        this.statusCode = statusCode.value();
        this.successMessage = successMessage;
        this.errorMessage = errorMessage;
    }

    private int statusCode;
    private String successMessage;
    private String errorMessage;
}
