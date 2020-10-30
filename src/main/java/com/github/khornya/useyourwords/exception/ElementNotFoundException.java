package com.github.khornya.useyourwords.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Element")
public class ElementNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
