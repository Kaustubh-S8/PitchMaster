package com.PitchMaster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PitchMaster.dto.RatingRequest;
import com.PitchMaster.dto.RatingResponse;
import com.PitchMaster.service.RatingService;


@RestController
@RequestMapping("/rating")
public class RatingController {
	
	private RatingService ratingService;
	
	public RatingController(RatingService ratingService) {
		this.ratingService=ratingService;
	}
	
	@PostMapping("/rate/userid/{uid}/presentationid/{pid}")
	public ResponseEntity<RatingResponse> ratingPresentation(@PathVariable Integer uid, @PathVariable Integer pid, @RequestBody RatingRequest request) {
		
		request.setUid(uid);
		request.setPid(pid);
		RatingResponse rat= ratingService.ratePresentation(request);
		
		return ResponseEntity.ok(rat);
	}
	
	@GetMapping("/presentationRating/{pid}")
	public ResponseEntity<?> get(@PathVariable Integer pid) {
		List<RatingResponse> ratings = ratingService.getRatingsByPresentationId(pid);
		if (ratings.isEmpty()) {
			return ResponseEntity.badRequest().body("No presentations found for user with ID: " + pid);
		}
		return ResponseEntity.ok(ratings);
	}

	@GetMapping("/studentPresentationRating/{uid}")
	public ResponseEntity<?> getAll(@PathVariable Integer uid) {
		List<RatingResponse> ratings = ratingService.getOverallRatingsByUserId(uid);
		if (ratings.isEmpty()) {
			return ResponseEntity.badRequest().body("No rating found for user with ID: " + uid);
		}
		return ResponseEntity.ok(ratings);

	}
}
