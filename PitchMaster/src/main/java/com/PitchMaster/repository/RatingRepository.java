package com.PitchMaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.PitchMaster.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
		
	
	  @Query("select r from Rating r where r.user.uid = ?1 and r.presentation.pid = ?2")
	   Rating findByUidAndPid(Integer uid, Integer pid);

	  @Query("select r from Rating r where r.presentation.pid = ?1")
	    List<Rating> findByPid(Integer pid);

	  @Query("select r from Rating r where r.user.uid = ?1")
	    List<Rating> findByUid(Integer uid);


}
