package com.PitchMaster.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PitchMaster.dto.PresentationResponse;
import com.PitchMaster.dto.UserResponse;
import com.PitchMaster.entity.Presentation;
import com.PitchMaster.enums.PresentationStatus;
import com.PitchMaster.enums.Role;
import com.PitchMaster.service.PresentationService;
import com.PitchMaster.service.UserService;



@RestController
@RequestMapping("/presentation")
public class PresentationController {
	
	private PresentationService preService ;
	private UserService userService;

	public PresentationController(PresentationService preService, UserService userService) {
		this.preService=preService;
		this.userService=userService;
	}
	
	@PostMapping("/Admin/addPresentation/userid/{uid}")
	public ResponseEntity<String> addPresentation(@PathVariable Integer uid, @RequestBody Presentation presentation) {
		
		ResponseEntity<String> add= preService.addPresentation(uid, presentation);
		
		return add;
	}
	
	
	@GetMapping("/presentationById")
	public ResponseEntity<?> getPresentationById(@RequestParam Integer pid) {
		
		PresentationResponse pre= preService.getPresentationById(pid);
		
		return ResponseEntity.ok(pre);
	}
	
	
	@GetMapping("/allPresentationByUid")
	public ResponseEntity<?> getAllPresentationByUid(@RequestParam Integer uid) {
		UserResponse user= userService.getUserByUid(uid);
		
		if (user.getRole() != Role.STUDENT) {
			return ResponseEntity.badRequest().body("Cannot access presentations for admin users");
		}
		List<PresentationResponse> pre= preService.getAllPresentationByUid(uid);
		if (pre.isEmpty()) {
			return ResponseEntity.badRequest().body("No presentations found for user with ID: " + uid);
		}
		
		return ResponseEntity.ok(pre);
	}
	
	
	

	@PutMapping("/{pid}/status")
	public ResponseEntity<String> changeStatus(@PathVariable Integer pid,
			@RequestParam PresentationStatus status) {

			preService.changePresentationStatus(pid, status);
			return ResponseEntity.ok("Presentation status updated successfully");
	}
	
	
	
	
}
