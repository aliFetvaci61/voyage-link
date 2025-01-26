package com.alifetvaci.travelmanagementservice.api.exception;

import com.alifetvaci.travelmanagementservice.api.BaseApiResponse;
import com.alifetvaci.travelmanagementservice.api.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<BaseApiResponse<?>> handleGenericException (GenericException ex){
        log.error("GlobalExceptionHandler -> handleGenericException(GenericException) is started, ex: {}", ex.getLogMessage());

        BaseApiResponse<?> response = new BaseApiResponse<>();
        response.setSuccess(false);
        response.setMessage(ex.getLogMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseApiResponse<?>> handleException (Throwable ex){
        log.error("GlobalExceptionHandler -> handleException(Exception) is started, ex: {}", ex.getMessage());

        BaseApiResponse<?> response = new BaseApiResponse<>();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation failed: {}", ex.getMessage());

        List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    return fieldName + ": " + message;
                })
                .collect(Collectors.toList());

        BaseApiResponse<List<String>> response = new BaseApiResponse<>();
        response.setSuccess(false);
        response.setMessage("Validation failed");
        response.setData(errorMessages);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
