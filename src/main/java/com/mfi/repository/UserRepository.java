package com.mfi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mfi.model.Customer;
import com.mfi.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	 @Query("select u from User u where u.name=?1")
	  List<User> findbyName(String name);
	 

	  @Query("select u from User u where  u.user_id=?1") 
	  User findbyCode(int code);
	  @Query("select u from User u where u.email=?1")
	  User findByEmail(String email);
	  
	 
	  User findByNrc(String nrc);
	  
	  @Query("select u from User u where u.password=?1")
	  User findUserByPass(String pass);
	  
}
