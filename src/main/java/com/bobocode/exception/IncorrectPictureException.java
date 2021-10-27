package com.bobocode.exception;

public class IncorrectPictureException extends RuntimeException {
    public IncorrectPictureException() {
        super("Submitted incorrect picture.");
    }
}
