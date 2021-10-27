package com.bobocode.exception;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException() {
        super("Invalid user data. Make sure you provide first and last names.");
    }
}
