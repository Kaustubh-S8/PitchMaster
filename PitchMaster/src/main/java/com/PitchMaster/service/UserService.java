package com.PitchMaster.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.PitchMaster.dto.UserRequest;
import com.PitchMaster.dto.UserResponse;
import com.PitchMaster.entity.User;
import com.PitchMaster.enums.Role;
import com.PitchMaster.enums.Status;
import com.PitchMaster.exception.UserNotFound;
import com.PitchMaster.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepo;

	public UserService(UserRepository userRepo) {
		this.userRepo=userRepo;
	}

	public boolean registerUser(UserRequest request) {
		Optional<User> opt= userRepo.findByEmail(request.getEmail());
		
		if (opt.isPresent()) {
			return false;
		} else {
			User user= new User();
			user.setStatus(Status.INACTIVE);
			BeanUtils.copyProperties(request, user);
			userRepo.save(user);
			return true;
		}
	}

	public boolean login(String email, String password) {
		
		User user= userRepo.findByEmail(email).orElseThrow(()-> new UserNotFound("User is not Register"));
		
		if(user.getPassword().equals(password)) {
			user.setStatus(Status.ACTIVE);
			userRepo.save(user);
			return true;
		}
		
		return false;
	}
	
	
	//checking status
	public UserResponse getUserByUid(Integer uid) {
		User user= userRepo.findById(uid).orElseThrow(()-> new UserNotFound("User not Found/ Enter register Uid"));
		if (user.getRole() == Role.ADMIN) {
			throw  new UserNotFound("Access to admin details is restricted");
		}
		
		UserResponse response = new UserResponse();
		BeanUtils.copyProperties(user, response);
		return response;
	}
	
	// without checking status
	public User fetchUserByUid(Integer uid) {
		User user= userRepo.findById(uid).orElseThrow(()-> new UserNotFound("User/Admin not Found / Enter register Uid"));
		
		
		return user;
	}

	public List<UserResponse> getAllUsers() {
		List<User> users =  userRepo.findAll();
		List<UserResponse> userResponseDto= new ArrayList<>();
		for(User user : users) {
			UserResponse Response= new UserResponse();
			BeanUtils.copyProperties(user, Response);
			userResponseDto.add(Response);
			
		}
		
		return userResponseDto;
	}

	public void updateStatus(Integer uid, Status status) {
		User user= userRepo.findById(uid).orElseThrow(()-> new UserNotFound("User not Found/ Enter register Uid"));

		if(user.getRole()==Role.ADMIN) throw new UserNotFound("Cannot change status of Another Admin");
		user.setStatus(status);
		userRepo.save(user);
		
	}

	
	
	
	
	
	
	
	
	
	

}
