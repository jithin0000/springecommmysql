package com.jithin.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(Long id) {
        super("no brand with this id"+id);
    }
}
