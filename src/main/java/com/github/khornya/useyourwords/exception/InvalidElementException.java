package com.github.khornya.useyourwords.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Un texte à trous doit contenir les caractères [...]")
public class InvalidElementException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
