package com.jithin.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ColorNotFoundException extends RuntimeException {
    public ColorNotFoundException(){
        super("no color with this id");
    }
}
