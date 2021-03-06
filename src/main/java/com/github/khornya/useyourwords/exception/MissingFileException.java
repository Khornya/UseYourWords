package com.github.khornya.useyourwords.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Un fichier doit être fourni pour ce type d'élément")
public class MissingFileException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
