package com.example.filedemo.service;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;

import com.example.filedemo.model.User;

public interface Iuserservice {

	public User loadUserByUsername(String username);
	public ArrayList<GrantedAuthority> loadRoleByUsername(String username);
	public User updateUser(User user);
	public User loadUserById(Long id);

}
