package com.PitchMaster.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.PitchMaster.dto.RatingRequest;
import com.PitchMaster.dto.RatingResponse;
import com.PitchMaster.entity.Presentation;
import com.PitchMaster.entity.Rating;
import com.PitchMaster.entity.User;
import com.PitchMaster.enums.PresentationStatus;
import com.PitchMaster.enums.Role;
import com.PitchMaster.exception.PresentationNotFound;
import com.PitchMaster.exception.UserNotFound;
import com.PitchMaster.repository.PresentationRepository;
import com.PitchMaster.repository.RatingRepository;
import com.PitchMaster.repository.UserRepository;

@Service
public class RatingService {
	
	private RatingRepository ratingRepo;
	private UserRepository userRepo;
	private PresentationRepository preRepo;
	

	public RatingService(RatingRepository ratingRepo, UserRepository userRepo, PresentationRepository preRepo) {
		this.ratingRepo = ratingRepo;
		this.userRepo = userRepo;
		this.preRepo = preRepo;
	}


	public RatingResponse ratePresentation(RatingRequest request) {
		User user= userRepo.findById(request.getUid()).orElseThrow(() -> new UserNotFound("User not found"));
			
		if (user.getRole() == Role.ADMIN) {
			throw new UserNotFound("Admins don't have any presentations.");
		}
		
		Presentation presentation = preRepo.findById(request.getPid()).orElseThrow(() -> new PresentationNotFound("Presentation not found"));
		if(presentation.getUser().getUid()!=user.getUid()) throw new PresentationNotFound("This user "+user.getUid()+" does not have presentation with id "+presentation.getPid());
		
		
			
		Rating	rating = new Rating();	
		
		BeanUtils.copyProperties(request, rating);
		rating.setUser(user);
		rating.setPresentation(presentation);
			
			// Calculate the average of all the ratings
			double averageScore = (rating.getCommunication() + rating.getContent() + rating.getInteraction() + rating.getLiveliness() + rating.getUsageProps()) / 5.0;
			rating.setTotalScore(averageScore);
			
			ratingRepo.save(rating);
			
			
			presentation.setPresentationStatus(PresentationStatus.COMPLETED);
			
			updatePresentationTotalScore(presentation.getPid());
			
			updateUserTotalScore(user.getUid());
			
			RatingResponse response = new RatingResponse();
			BeanUtils.copyProperties(rating, response);
			response.setUid(user.getUid());
			response.setPid(presentation.getPid());
			
			return response;
			
	}
	
	private void updateUserTotalScore(Integer uid) {
		List<Presentation> presentations = preRepo.findByUid(uid);

		List<Presentation> completedPresentations = new ArrayList<>();
		for (Presentation presentation : presentations) {
			if (presentation.getPresentationStatus() == PresentationStatus.COMPLETED && presentation.getUserTotalScore() != null && presentation.getUserTotalScore() > 0) {
				completedPresentations.add(presentation);
			}
		}

		double userTotalScore = 0.0;
		int count = 0;
		for (Presentation presentation : completedPresentations) {
			userTotalScore += presentation.getUserTotalScore();
			count++;
		}

		if (count > 0) {
			userTotalScore /= count;
		}

		User user = userRepo.findById(uid).orElseThrow(() -> new UserNotFound("User not found"));

		user.setUserTotalScore(userTotalScore);
		userRepo.save(user);
		
	}


	private void updatePresentationTotalScore(Integer pid) {
		List<Rating> ratings = ratingRepo.findByPid(pid);

		double totalScore = 0.0;
		int count = 0;
		for (Rating rating : ratings) {
			totalScore += rating.getTotalScore();
			count++;
		}

		if (count > 0) {
			totalScore /= count;
		}

		Presentation presentation = preRepo.findById(pid).orElseThrow(() -> new PresentationNotFound("Presentation not found"));

		presentation.setUserTotalScore(totalScore);
		preRepo.save(presentation);
	}


	public List<RatingResponse> getRatingsByPresentationId(Integer pid) {
		List<Rating> ratings = ratingRepo.findByPid(pid);
		List<RatingResponse> responseList = new ArrayList<>();

		for (Rating rating : ratings) {
			RatingResponse response = new RatingResponse();
			BeanUtils.copyProperties(rating, response);
			response.setPid(pid);
			response.setUid(rating.getUser().getUid());
			responseList.add(response);
		}

		return responseList;
	}


	public List<RatingResponse> getOverallRatingsByUserId(Integer uid) {
		List<Rating> ratings = ratingRepo.findByUid(uid);
		List<RatingResponse> responseList = new ArrayList<>();

		for (Rating rating : ratings) {
			RatingResponse response = new RatingResponse();
			BeanUtils.copyProperties(rating, response);
			response.setUid(uid);
			response.setPid(rating.getPresentation().getPid());
			responseList.add(response);
		}

		return responseList;
	}


}
