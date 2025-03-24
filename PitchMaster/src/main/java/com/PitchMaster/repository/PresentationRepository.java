package com.PitchMaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PitchMaster.entity.Presentation;

public interface PresentationRepository extends JpaRepository<Presentation, Integer> {

	 @Query("select p from Presentation p where p.user.uid = ?1")
	    List<Presentation> findByUid(Integer uid);


	
	

}
