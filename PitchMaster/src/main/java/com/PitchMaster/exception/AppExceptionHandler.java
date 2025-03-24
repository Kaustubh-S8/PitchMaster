package com.PitchMaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<String> catchUserNotFound(UserNotFound message){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message.getMessage());
	}
	
	@ExceptionHandler(PresentationNotFound.class)
	public ResponseEntity<String> catchPresentationNotFoundException(PresentationNotFound message) {
		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(message.getMessage());
	}
	
	@ExceptionHandler(RatingNotFound.class)
	public ResponseEntity<String> catchRatingNotFoundException(RatingNotFound message) {
		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(message.getMessage());
	}
}
