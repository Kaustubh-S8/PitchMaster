package com.PitchMaster.dto;

import com.PitchMaster.enums.PresentationStatus;

import lombok.Data;

@Data
public class PresentationResponse {
	  private Integer pid;
	    private Integer uid;
	    private String course;
	    private PresentationStatus presentationStatus;
	    private Double presentationTotalScore;
}
