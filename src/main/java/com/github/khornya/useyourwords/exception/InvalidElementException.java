package com.github.khornya.useyourwords.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Le format de cet élément est invalide")
public class InvalidElementException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
