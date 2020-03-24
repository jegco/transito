package com.tesis.transito.utils;

import com.tesis.dominio.utils.exceptions.UnAuthorizedException;
import com.tesis.dominio.utils.exceptions.UserAlreadyExistException;
import com.tesis.dominio.utils.exceptions.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Provides handling for exceptions throughout this service.
     */
    @ExceptionHandler({UserNotFoundException.class, UserAlreadyExistException.class, UnAuthorizedException.class})
    public final ResponseEntity<ApiError> handleException(Exception ex) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof UserNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            UserNotFoundException unfe = (UserNotFoundException) ex;

            return handleBusinessException(unfe, headers, status);
        } else if (ex instanceof UserAlreadyExistException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            UserAlreadyExistException cnae = (UserAlreadyExistException) ex;

            return handleBusinessException(cnae, headers, status);
        } else if (ex instanceof UnAuthorizedException) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            UnAuthorizedException cnae = (UnAuthorizedException) ex;
            return handleBusinessException(cnae, headers, status);
        } else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleExceptionInternal(ex, null, headers, status);
        }
    }

    /**
     * Customize the response for UserNotFoundException.
     */
    private ResponseEntity<ApiError> handleBusinessException(Exception ex, HttpHeaders headers, HttpStatus status) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), headers, status);
    }


    /**
     * A single place to customize the response body of all Exception types.
     */
    private ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status) {
        return new ResponseEntity<>(body, headers, status);
    }
}
