package org.statistics.exception;

import org.statistics.common.ApiException;

public final class ValidationException extends RuntimeException {
    private Integer errorCode;
    private String errorMessage;

    public ValidationException(ApiException apiException){
        super(apiException.code() + "-" + apiException.message());

        this.errorCode = apiException.code();
        this.errorMessage = apiException.message();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
