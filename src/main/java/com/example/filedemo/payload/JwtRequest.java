package com.example.filedemo.payload;

import java.io.Serializable;

import com.example.filedemo.model.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {

private static final long serialVersionUID = 5926468583005150707L;
	
	private String token;
	private String username;
	private String password;
	private String Tel;
	private String email;
	private String image;
	private Roles roleUser;
	private long iduser;

	
}
