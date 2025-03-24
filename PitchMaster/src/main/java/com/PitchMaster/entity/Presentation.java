package com.PitchMaster.entity;

import com.PitchMaster.enums.PresentationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Presentation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pid;
	
	
	private String course;
	
	private String topic;
	
	@Enumerated(EnumType.STRING)
	private PresentationStatus presentationStatus;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
	
	private Double userTotalScore;
}
