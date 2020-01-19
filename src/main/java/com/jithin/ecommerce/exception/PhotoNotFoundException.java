package com.jithin.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhotoNotFoundException extends RuntimeException {

    public PhotoNotFoundException(Long photoId) {
        super("no photo with this id");
    }
}
