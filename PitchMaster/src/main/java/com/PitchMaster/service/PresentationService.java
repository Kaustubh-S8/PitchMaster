package com.PitchMaster.service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.PitchMaster.dto.PresentationResponse;
import com.PitchMaster.entity.Presentation;
import com.PitchMaster.entity.User;
import com.PitchMaster.enums.PresentationStatus;
import com.PitchMaster.enums.Role;
import com.PitchMaster.exception.PresentationNotFound;
import com.PitchMaster.exception.UserNotFound;
import com.PitchMaster.repository.PresentationRepository;
import com.PitchMaster.repository.UserRepository;

@Service
public class PresentationService {
	
	private PresentationRepository preRepo;
	
	private UserRepository userRepo;
	
	

	public PresentationService(PresentationRepository preRepo, UserRepository userRepo) {
		this.preRepo = preRepo;
		this.userRepo = userRepo;
	}



	public ResponseEntity<String> addPresentation(Integer uid, Presentation presentation) {
		User user= userRepo.findById(uid).orElseThrow(() -> new UserNotFound("User Not Found"));
		
		if (user.getRole() == Role.ADMIN) {
			return ResponseEntity.badRequest().body("Cannot assign presentation to Admin");
		}
		
		presentation.setUser(user);
		presentation.setPresentationStatus(PresentationStatus.ASSIGNED);
		
		preRepo.save(presentation);
		return ResponseEntity.ok().body("Presentation added successfully");
	}



	public PresentationResponse getPresentationById(Integer pid) {
		Presentation pre= preRepo.findById(pid).orElseThrow(() -> new PresentationNotFound("Presentation Not Found / Enter valid pid"));
		PresentationResponse response = new PresentationResponse();
        BeanUtils.copyProperties(pre, response);
        response.setUid(pre.getUser().getUid());
		
		return response;
	}



	public List<PresentationResponse> getAllPresentationByUid(Integer uid) {
		List<Presentation> pre = preRepo.findByUid(uid);
		List<PresentationResponse> presentationResponseDtos = new ArrayList<>();
		for (Presentation presentation : pre) {
            PresentationResponse response = new PresentationResponse();
            BeanUtils.copyProperties(presentation, response);
            response.setUid(uid);
            presentationResponseDtos.add(response);
        }
        
        return presentationResponseDtos;
	}



	public void changePresentationStatus(Integer pid, PresentationStatus status) {
		Presentation pre=preRepo.findById(pid).orElseThrow(() -> new PresentationNotFound("Presentation Not Found / Enter valid pid"));
		if(pre.getPresentationStatus() == PresentationStatus.COMPLETED) throw new PresentationNotFound("Already completed presentation status cannot be change");
		pre.setPresentationStatus(status);
        preRepo.save(pre);
		
	}
	
	
	



}
