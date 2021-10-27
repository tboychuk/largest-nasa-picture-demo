package com.bobocode.controller;

import com.bobocode.dto.ErrorResponse;
import com.bobocode.exception.CorrectPictureAlreadySubmittedException;
import com.bobocode.exception.IncorrectPictureException;
import com.bobocode.exception.InvalidUserDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PictureControllerAdvice {

    @ExceptionHandler({
            IncorrectPictureException.class,
            InvalidUserDataException.class,
            CorrectPictureAlreadySubmittedException.class
    })
    public ResponseEntity<ErrorResponse> handleIncorrectPicture(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }
}
