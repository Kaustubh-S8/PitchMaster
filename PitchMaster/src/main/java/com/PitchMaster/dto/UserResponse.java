package com.PitchMaster.dto;

import com.PitchMaster.enums.Role;
import com.PitchMaster.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
	
	private Integer uid;
	
	private String name;
	
	private String email;
	
	private Long phone;
	
	private Status status;
	private Role role;
	
	private Double userTotalScore;
}
