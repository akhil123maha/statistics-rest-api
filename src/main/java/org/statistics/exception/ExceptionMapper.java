package org.statistics.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.statistics.common.ApiException;
import org.statistics.model.ErrorResponse;

@ControllerAdvice
public class ExceptionMapper {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionMapper.class);

    @ResponseBody
    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(ValidationException exception){
        logger.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getErrorCode(), exception.getErrorMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return new ErrorResponse(ApiException.UNEXPECTED_ERROR.code(), ApiException.UNEXPECTED_ERROR.message());
    }

    @ResponseBody
    @ExceptionHandler(value = OutdatedTransactionException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleException(OutdatedTransactionException exception) {
        logger.error(exception.getMessage(), exception);
    }
}
