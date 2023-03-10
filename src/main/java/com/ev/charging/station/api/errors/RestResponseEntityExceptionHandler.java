package com.ev.charging.station.api.errors;

import com.ev.charging.station.api.entity.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<ErrorMessage> stationNotFoundException(StationNotFoundException stationNotFoundException, WebRequest webRequest) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, stationNotFoundException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorMessage> fileFormatInvalidException(FileNotFoundException fileNotFoundException, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, fileNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorMessage> jsonProcessingException(JsonProcessingException jsonProcessingException, WebRequest webRequest) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST, jsonProcessingException.getMessage());
        System.out.println(jsonProcessingException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
