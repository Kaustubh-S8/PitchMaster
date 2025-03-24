package com.PitchMaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PitchMaster.entity.User;



public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
	

}
