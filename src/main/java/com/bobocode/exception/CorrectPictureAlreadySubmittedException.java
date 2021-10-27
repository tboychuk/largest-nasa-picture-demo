package com.bobocode.exception;

public class CorrectPictureAlreadySubmittedException extends RuntimeException {
    public CorrectPictureAlreadySubmittedException() {
        super("Correct picture has been already submitted from your address.");
    }
}
