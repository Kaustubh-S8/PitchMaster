package com.PitchMaster.dto;


import com.PitchMaster.enums.Role;
import com.PitchMaster.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
	
	private String name;

	private String email;

	private Long phone;

	private String password;
	
	private Role role;
	
	private Status status;
	
	private Double userTotalScore;
}
