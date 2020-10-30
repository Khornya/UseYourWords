package com.github.khornya.useyourwords.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(value = { MaxUploadSizeExceededException.class })
    public ModelAndView handleMaxSizeException(
            MaxUploadSizeExceededException ex,
            HttpServletRequest request,
            HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", String.format("Le fichier dépasse les %s Mo !", ex.getMaxUploadSize() / 1000000));
        return modelAndView;
    }
}
