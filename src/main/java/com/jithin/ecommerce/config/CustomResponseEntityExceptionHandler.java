package com.jithin.ecommerce.config;

import com.jithin.ecommerce.dto.UserNameAlreadyExistResponse;
import com.jithin.ecommerce.exception.UserNameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleUserNameAlreadyExists(UserNameAlreadyExistException ex,
                                                                    WebRequest request) {
        UserNameAlreadyExistResponse response = new UserNameAlreadyExistResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
