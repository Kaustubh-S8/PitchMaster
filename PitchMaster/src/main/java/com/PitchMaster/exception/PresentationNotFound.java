package com.PitchMaster.exception;

public class PresentationNotFound extends RuntimeException{
    private final String message;

    public PresentationNotFound(String message) {
        super(message); // Call the superclass constructor
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message; // Return the custom message
    }
	
}
