package com.PitchMaster.exception;

public class UserNotFound extends RuntimeException {
    private String message;

    public UserNotFound(String message) {
        super(message); // Call the superclass constructor
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message; // Return the custom message
    }
}
