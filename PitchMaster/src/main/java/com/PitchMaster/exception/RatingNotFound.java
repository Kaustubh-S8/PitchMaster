package com.PitchMaster.exception;

public class RatingNotFound extends RuntimeException {
    private final String message;

    public RatingNotFound(String message) {
        super(message); // Call the superclass constructor
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message; // Return the custom message
    }

}
