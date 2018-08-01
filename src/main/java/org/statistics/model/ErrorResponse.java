package org.statistics.model;

public class ErrorResponse {
    private Integer errorCode;
    private String errorMessage;

    public ErrorResponse(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
