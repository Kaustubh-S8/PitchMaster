package com.PitchMaster.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PitchMaster.dto.UserRequest;
import com.PitchMaster.dto.UserResponse;
import com.PitchMaster.entity.User;
import com.PitchMaster.enums.Role;
import com.PitchMaster.enums.Status;
import com.PitchMaster.service.UserService;





@RestController
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping(value = "/register", consumes = { "application/json", "application/xml" },
			  produces = {"application/json", "application/xml" }
)
	public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
		boolean registered = userService.registerUser(userRequest);
		if(registered) {
			return new ResponseEntity<String>("register", HttpStatus.CREATED);
		}
		return ResponseEntity.badRequest().body("already registered");
		
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
		
		boolean login = userService.login(email, password);
		
		if(login) {
			return new ResponseEntity<String>("Login Successful", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Login Failed", HttpStatus.BAD_REQUEST);
	}
	
	
	
	@GetMapping("/Udetails")
	public ResponseEntity<?> Udetails(@RequestParam Integer uid) {
		
		UserResponse user= userService.getUserByUid(uid);
		if (user.getStatus() == Status.INACTIVE) {
			return ResponseEntity.badRequest().body("Inactive user cannot perform this action");
		}
		return new ResponseEntity<UserResponse>(user,HttpStatus.OK);
	}
	
	
//	TO display all the users for admin
	@GetMapping("/Admin/{uid}/allDetails")
	public ResponseEntity<?> UAlldetails(@PathVariable Integer uid) {
		User user= userService.fetchUserByUid(uid);
		if (user.getRole() != Role.ADMIN) {
			return ResponseEntity.badRequest().body("Access to details is restricted");
		}
		List<UserResponse> users= userService.getAllUsers();
		return new ResponseEntity<List<UserResponse>>(users, HttpStatus.OK);
	}
	
	
	
	// update status by admin
	@PutMapping("/Admin/{adminid}/changeStatus")
	public ResponseEntity<String> changeStatus(@PathVariable Integer adminid, @RequestParam Integer uid, @RequestParam Status status) {
		User admin= userService.fetchUserByUid(adminid);
		
		if (admin.getRole() != Role.ADMIN) {
			return ResponseEntity.badRequest().body("Only admins can update user statuses.");
		}
		
		userService.updateStatus(uid, status);
		return ResponseEntity.ok("User status updated successfully.");
	}
	
	
	

	
	

}
