package com.example.filedemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.filedemo.model.User;
import com.example.filedemo.repository.UserRepository;

@Service
public class UserService implements Iuserservice {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userrep;

	@Override
	public User loadUserByUsername(String username) {
		return userrep.findByUsername(username);
	}

	public User saveUser(User user) {

		String hashPW = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hashPW);
		user = userrep.save(user);
		return user;
	}

	@Override
	public ArrayList<GrantedAuthority> loadRoleByUsername(String username) {
		User user = loadUserByUsername(username);
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRoleUser().toString()));
		return authorities;
	}

	@Override
	public User updateUser(User user) {
		return userrep.save(user);
	}

	@Override
	public User loadUserById(Long id) {
		return userrep.findByIduser(id);
	}
}
