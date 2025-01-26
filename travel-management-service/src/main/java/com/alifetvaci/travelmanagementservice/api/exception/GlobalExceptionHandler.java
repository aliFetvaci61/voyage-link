package com.alifetvaci.travelmanagementservice.api.exception;

import com.alifetvaci.travelmanagementservice.api.BaseApiResponse;
import com.alifetvaci.travelmanagementservice.api.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(GenericException.class)
    public BaseApiResponse<?> handleGenericException (GenericException ex){
        log.error("GlobalExceptionHandler -> handleGenericException(GenericException) is started, ex: {}", ex.getLogMessage());

        BaseApiResponse<?> response = new BaseApiResponse<>();
        response.setSuccess(false);
        response.setMessage(ex.getLogMessage());
        return response;
    }

    @ExceptionHandler(Throwable.class)
    public BaseApiResponse<?> handleException (Throwable ex){
        log.error("GlobalExceptionHandler -> handleException(Exception) is started, ex: {}", ex.getMessage());

        BaseApiResponse<?> response = new BaseApiResponse<>();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        return response;
    }

}
