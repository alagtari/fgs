package com.example.filedemo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.filedemo.config.JwtTokenUtil;
import com.example.filedemo.model.Colis;
import com.example.filedemo.model.Fournisseur;
import com.example.filedemo.model.User;
import com.example.filedemo.payload.JwtRequest;
import com.example.filedemo.repository.UserRepository;
import com.example.filedemo.service.Iuserservice;
import com.example.filedemo.service.UserService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	@Value("${images.direcotry.path}")
	private String imagesDirectory;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	ServletContext context;
	@Autowired
	Iuserservice userse;
	@Autowired
	UserRepository repository;
	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody() User user) throws IOException {
		User userPostRegister =null;
		try {
			userPostRegister = userService.saveUser(user);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(userPostRegister);
	}

	@PostMapping(value = "/login")
	public ResponseEntity<JwtRequest> register(@RequestBody JwtRequest loginUser) throws AuthenticationException {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
		final User user = userse.loadUserByUsername(loginUser.getUsername());
		if (user != null) {
			final String token = jwtTokenUtil.generateToken(user);
			return new ResponseEntity<JwtRequest>(new JwtRequest(token, user.getUsername(), user.getPassword(),
					user.getTel(), user.getEmail(), user.getImage(), user.getRoleUser(), user.getIduser()),
					HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<JwtRequest>(new JwtRequest(null, null, null, null, null, null, null, 0),
				HttpStatus.UNAUTHORIZED);
	}

	@GetMapping(value = "/findRolesByUsername/{username}")
	public ResponseEntity findRolesByUsername(@PathVariable(value = "username") String username) {
		ArrayList<GrantedAuthority> aythorities =new ArrayList<>();
		try {
			aythorities = userse.loadRoleByUsername(username);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(aythorities);
	}

	@GetMapping(value = "/imageUser/{id}")
	public ResponseEntity getPhoto(@PathVariable("id") Long id) throws Exception {
		byte[] image=null;
		try {
			User user = repository.findById(id).get();
			image = Files.readAllBytes(Paths.get(imagesDirectory +"\\"+ user.getImage()));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(image);	
	}

	/*@GetMapping(value = "/generateUniqueUsername/{nom}")
	public ResponseEntity generateUniqueUsername(@PathVariable(value = "nom") String nom) {
		String username=null;
		try {
			username = userse.generateUsername(nom);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(username);
	}*/

	@PutMapping("/updateUserWithImage")
	public ResponseEntity updateUserWithImage(@RequestParam("image") MultipartFile file,
			@RequestParam("user") String userStr) throws JsonParseException, JsonMappingException, Exception {
		User userPostUpdate = null;
		try {
			User user= new ObjectMapper().readValue(userStr, User.class);
			boolean isExit = new File(imagesDirectory).exists();
			if (!isExit) {
				new File(imagesDirectory).mkdir();
			}
			userPostUpdate = updateUser(user, file);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(userPostUpdate);
	}

	@PutMapping("/updateUser")
	public ResponseEntity updateUserWithImage(@RequestBody User user)
			throws JsonParseException, JsonMappingException, Exception {
		User userPostUpdate = null;
		try {
			userPostUpdate = updateUser(user, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(userPostUpdate);
	}

	private String saveFile(MultipartFile file) throws IOException {
		String filename = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
		File serverFile = new File(imagesDirectory + "\\" + File.separator + newFileName);
		FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
		return newFileName;
	}

	private User updateUser(User user, MultipartFile file) {
		User persistentUser = userse.loadUserById(user.getIduser());
		User checkUser = userse.loadUserByUsername(user.getUsername());
		if (checkUser != null && persistentUser != null
				&& !checkUser.getUsername().equals(persistentUser.getUsername())) {
			throw new RuntimeException("username exists");
		}
		if (persistentUser != null) {
			try {
				if (file == null) {
					user.setImage(persistentUser.getImage());
				} else {
					user.setImage(saveFile(file));
				}
				user.setRoleUser(persistentUser.getRoleUser());
				user.setPassword(persistentUser.getPassword());
				return userse.updateUser(user);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("account does not exist");
		}
	}
}
