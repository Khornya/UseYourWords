package com.github.khornya.useyourwords.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="A file must be provided for this element")
public class MissingFileException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
