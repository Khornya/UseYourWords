package com.github.khornya.useyourwords.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Score")
public class ScoreNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
}
