package com.example.filedemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.filedemo.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	 public User findByUsername(String username);
	 @Query("SELECT u.username FROM User u")
	  public List<String> LoadAllUsernames();
	 
	 User findByIduser(Long id);
	
}
