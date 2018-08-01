package org.statistics.common;

public enum ApiException {

    VALIDATION_EMPTY_REQUEST_BODY(501, "Empty request body"),
    VALIDATION_MISSING_TIMESTAMP(502, "Missing timestamp field"),
    VALIDATION_MISSING_AMOUNT(503, "Missing amount field"),
    UNEXPECTED_ERROR(504, "Internal API Error"),

    OUTDATED_TRANSACTION(204, "Outdated Transaction");

    private Integer code;
    private String message;

    ApiException(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer code(){
        return this.code;
    }

    public String message(){
        return this.message;
    }
}
