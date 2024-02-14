package com.lcwd.electronic.store.exceptions;

import com.lcwd.electronic.store.dto.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();

        allErrors.stream().forEach(objectOfError -> {
            String message = objectOfError.getDefaultMessage();
            String field = ((FieldError) objectOfError).getField();

            response.put(field, message);
        });
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
