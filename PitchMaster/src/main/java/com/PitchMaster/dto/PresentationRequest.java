package com.PitchMaster.dto;

import com.PitchMaster.enums.PresentationStatus;

import lombok.Data;

@Data
public class PresentationRequest {
	private Integer rid;

  private String course;

  private PresentationStatus presentationStatus;
}
