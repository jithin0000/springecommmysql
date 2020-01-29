package com.jithin.ecommerce.config;

import com.jithin.ecommerce.dto.UserNameAlreadyExistResponse;
import com.jithin.ecommerce.exception.UserNameAlreadyExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError)error).getField();
            String errorString = ex.getMessage();
            errors.put(fieldName, errorString);

        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNameAlreadyExists(UserNameAlreadyExistException ex,
                                                                    WebRequest request) {
        UserNameAlreadyExistResponse response = new UserNameAlreadyExistResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
