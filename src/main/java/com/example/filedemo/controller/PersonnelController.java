package com.example.filedemo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.filedemo.model.Personnel;
import com.example.filedemo.model.User;
import com.example.filedemo.payload.ChangePasswordPayload;
import com.example.filedemo.repository.PersonnelRepository;
import com.example.filedemo.repository.UserRepository;
import com.example.filedemo.service.HubService;
import com.example.filedemo.service.PersonnelService;
import com.example.filedemo.service.UserService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class PersonnelController {
	@Value("${images.direcotry.path}")
	private String imagesDirectory;
	@Autowired
	PersonnelService personnelService;

	@Autowired
	ServletContext context;

	@Autowired
	PersonnelRepository personnelRepository;

	@Autowired
	HubService hubService;
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("/retrieveAllPersonnels")
	@ResponseBody
	public ResponseEntity getPersonnel() {
		List<Personnel> personnels = new ArrayList<>();
		try {
			personnels = personnelService.retrieveAllPersonnels();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(personnels);
	}

	@GetMapping("/retrievePersonnel/{personnelId}")
	@ResponseBody
	public ResponseEntity retrievePersonnel(@PathVariable("personnelId") long id) {
		Personnel personnel = null;
		try {
			personnel = personnelService.retrievePersonnel(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(personnel);
	}

	@PostMapping("/addPersonnel")
	@ResponseBody
	public ResponseEntity addPersonnel(@RequestBody Personnel personnel) {
		Personnel personnelPostSave = null;
		try {
			personnelPostSave = personnelService.addPersonnel(personnel);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(personnelPostSave);
	}

	@DeleteMapping("/removePersonnel/{id}")
	@ResponseBody
	public ResponseEntity removePersonnel(@PathVariable("id") Long id) {
		try {
			personnelService.deletePersonnel(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("personnel deleted");
	}

	@CrossOrigin("*")
	@PutMapping("/updatepersonnel/{iduser}")
	public ResponseEntity updatePersonnel(@Valid @RequestBody Personnel personnelUpdated, @PathVariable("iduser") Long iduser)
			throws Exception {
		Personnel personnelPostUpdate = null;
		try {
			personnelPostUpdate = personnelRepository.findById(iduser).map(personnel -> {
				personnel.setEmail(personnelUpdated.getEmail());
				personnel.setNom(personnelUpdated.getNom());
				personnel.setPrenom(personnelUpdated.getPrenom());
				if (personnelUpdated.getHub() != null) {
					personnel.setHub(hubService.retrieveHub(personnelUpdated.getHub().getId_hub()));
				}
				personnel.setMatricule_veh(personnelUpdated.getMatricule_veh());
				personnel.setTel(personnelUpdated.getTel());
				return personnelRepository.save(personnel);
			}).orElseThrow(() -> new Exception("Personnel inexistant !"));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(personnelPostUpdate);
	}

	@GetMapping("/getLivreurList")
	public ResponseEntity getLivreurList() {
		List<Personnel> personnels = new ArrayList<>();
		try {
			personnels = personnelService.getLivreurList();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(personnels);
	}

	@GetMapping("/getLivreurListByHub/{id}")
	public ResponseEntity getLivreurListByHub(@PathVariable("id") Long id) {
		List<Personnel> personnels = new ArrayList<>();
		try {
			personnels = personnelService.getLivreurListByHub(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(personnels);
	}

	@PostMapping("/addPersonnelWithImageAndPermisAndCarteG")
	public ResponseEntity createArticle(@RequestParam("image") MultipartFile image,
			@RequestParam("carteGrise") MultipartFile carteGrise, @RequestParam("permis") MultipartFile permis,
			@RequestParam("personnel") String personnel) throws JsonParseException, JsonMappingException, Exception {
		Personnel savedPersonnel = null;
		try {
			Personnel parsedPersonnel = new ObjectMapper().readValue(personnel, Personnel.class);
			if(userService.loadUserByUsername(parsedPersonnel.getEmail())!=null) {
				return ResponseEntity.badRequest().body("Email exists");
			}
			boolean isExit = new File(imagesDirectory).exists();
			if (!isExit) {
				new File(imagesDirectory).mkdir();
			}
			parsedPersonnel.setPermis(saveFile(permis));
			parsedPersonnel.setCarte_grise(saveFile(carteGrise));
			parsedPersonnel.setImage(saveFile(image));
			savedPersonnel = personnelService.addPersonnel(parsedPersonnel);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		if (savedPersonnel != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonnel);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Personnel not saved");
		}
	}

	@GetMapping(path = "/imagePersonnel/{id}")
	public ResponseEntity getPhoto(@PathVariable("id") Long id) throws Exception {
		Personnel personnel = null;
		try {
			personnel = personnelRepository.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(Files.readAllBytes(Paths.get(imagesDirectory + "\\" + personnel.getImage())));
	}

	@GetMapping(path = "/carteGrisePersonnel/{id}")
	public ResponseEntity getcartegrise(@PathVariable("id") Long id) throws Exception {
		Personnel personnel = null;
		try {
			personnel = personnelRepository.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(Files.readAllBytes(Paths.get(imagesDirectory + "\\" + personnel.getCarte_grise())));
	}

	@GetMapping(path = "/permisPersonnel/{id}")
	public ResponseEntity getpermis(@PathVariable("id") Long id) throws Exception {
		Personnel personnel = null;
		try {
			personnel = personnelRepository.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(Files.readAllBytes(Paths.get(imagesDirectory + "\\" + personnel.getPermis())));
	}

	@PutMapping("/updatePersonnelImage")
	public ResponseEntity updatePersonnelPhoto(@RequestParam("image") MultipartFile image,
			@RequestParam("personnel") String personnel) throws JsonParseException, JsonMappingException, Exception {
		Personnel personnelPostUpdate = null;
		try {
			Personnel parsedPersonnel = new ObjectMapper().readValue(personnel, Personnel.class);
			boolean isExit = new File(imagesDirectory).exists();
			if (!isExit) {
				new File(imagesDirectory).mkdir();
			}
			personnelPostUpdate = personnelRepository.findById(parsedPersonnel.getIduser()).map(existingPersonnel -> {
				existingPersonnel.setEmail(parsedPersonnel.getEmail());
				try {
					existingPersonnel.setImage(saveFile(image));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				existingPersonnel.setNom(parsedPersonnel.getNom());
				existingPersonnel.setPrenom(parsedPersonnel.getPrenom());
				if (parsedPersonnel.getHub() != null) {
					existingPersonnel.setHub(hubService.retrieveHub(parsedPersonnel.getHub().getId_hub()));
				}
				existingPersonnel.setMatricule_veh(parsedPersonnel.getMatricule_veh());
				existingPersonnel.setTel(parsedPersonnel.getTel());
				return personnelRepository.save(existingPersonnel);
			}).orElseThrow(() -> new Exception("Personnel inexistant !"));
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(personnelPostUpdate);

	}
	@PutMapping("/changePasswordPersonnel")
	public ResponseEntity changePassword(@RequestBody ChangePasswordPayload changePasswordPayload) {	
		Personnel personnel =null;
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user0 = userRepository.findByUsername(auth.getName());
			personnel=personnelService.changePassWord((long) user0.getIduser(), changePasswordPayload.getPassword());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		HttpStatus status = personnel==null?HttpStatus.NOT_FOUND:HttpStatus.OK;
		return ResponseEntity.status(status).body(personnel==null?"personnel not found":personnel);

	}
	private String saveFile(MultipartFile file) throws IOException {
		String filename = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
		File serverFile = new File(imagesDirectory + "\\" + File.separator + newFileName);
		FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
		return newFileName;
	}
}
