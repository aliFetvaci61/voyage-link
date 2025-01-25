package com.alifetvaci.travelmanagementservice.api.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@Builder
public class GenericException extends RuntimeException {

    private ErrorCode errorCode;
    private HttpStatus httpStatus;
    private String logMessage;
}
