package com.example.filedemo.controller;

import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.example.filedemo.model.User;
import com.example.filedemo.payload.ChangePasswordPayload;
import com.example.filedemo.repository.UserRepository;
import org.springframework.boot.json.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FilenameUtils;
import org.aspectj.bridge.AbortException;
import org.apache.commons.io.FileUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.filedemo.model.Colis;
import com.example.filedemo.model.Fournisseur;
import com.example.filedemo.model.Personnel;
import com.example.filedemo.model.Response;
import com.example.filedemo.repository.FournisseurRepository;
import com.example.filedemo.service.FournisseurService;
import com.example.filedemo.service.UserService;

@Controller
@RestController
@CrossOrigin("*")
@RequestMapping
public class FournisseurController {
	@Value("${images.direcotry.path}")
	private String imagesDirectory;
	
	@Autowired
	private final FournisseurService fournisseurService;

	@Autowired
	ServletContext context;

	@Autowired
	FournisseurRepository fournisseurRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	public FournisseurController(FournisseurService fournisseurService) {
		this.fournisseurService = fournisseurService;
	}

	// find all
	@GetMapping("/findAllFournisseur")
	public ResponseEntity getAllFournisseur() {
		List<Fournisseur> fournisseurs = null;
		try {
			fournisseurs = fournisseurService.getAllFournisseur();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(fournisseurs);
	}

	// find by id
	@GetMapping("/findFournisseurById/{iduser}")
	public ResponseEntity findById(@PathVariable Long iduser) {
		Fournisseur fournisseur = null;
		try {
			fournisseur = fournisseurRepository.findById(iduser).get();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(fournisseur);
	}

	// register fournisseur
	@PostMapping("/addFournisseur")
	public ResponseEntity register(@RequestBody() Fournisseur fournisseur) throws IOException {
		Fournisseur fournisseurPostSave = null;
		try {
			if(userService.loadUserByUsername(fournisseur.getEmail())!=null) {
				return ResponseEntity.badRequest().body("Email exists");
			}
			fournisseurPostSave = fournisseurService.saveFour(fournisseur);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(fournisseurPostSave);
	}

	// delete
	@DeleteMapping("/deleteLogiqueFournisseur/{id}")
	public ResponseEntity deleteFournisseur(@PathVariable(value = "id") Long id) {
		try {
			fournisseurService.deleteLogiqueFournisseur(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("fournisseur Deleted");
	}

	@CrossOrigin("*")
	@PutMapping("/updatefournisseur/{iduser}")
	public ResponseEntity updateFournisseur(@Valid @RequestBody Fournisseur fournisseurUpdated,
			@PathVariable Long iduser) throws Exception {
		Fournisseur fournisseurPostUpdate = null;
		try {
			if(fournisseurUpdated.getEmail()!=null) {
				fournisseurUpdated.setEmail(fournisseurUpdated.getEmail());
			} 
			/*if(fournisseurUpdated.getEmail_f()!=null && 
					userService.loadUserByUsername(fournisseurUpdated.getEmail_f())!=null) {	
				return ResponseEntity.badRequest().body("Email exists");
			}*/
			fournisseurPostUpdate = updateAndMapFournisseur(iduser, fournisseurUpdated);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(fournisseurPostUpdate);
	}

	@PostMapping("/addFournisseurWithLogoAndPatente")
	public ResponseEntity addFournisseurWithImage(@RequestParam("file") MultipartFile file,
			@RequestParam("patente") MultipartFile patente, @RequestParam("fournisseur") String fournisseur)
			throws JsonParseException, JsonMappingException, Exception {
		Fournisseur savedFournisseur = null;
		try {
			Fournisseur fournisseurToBeSaved = new ObjectMapper().readValue(fournisseur, Fournisseur.class);
			if(userService.loadUserByUsername(fournisseurToBeSaved.getEmail())!=null) {
				return ResponseEntity.badRequest().body("Email exists");
			}
			boolean isExist = new File(imagesDirectory).exists();
			if (!isExist) {
				new File(imagesDirectory).mkdir();
			}
			String newFileName = saveFile(file);
			fournisseurToBeSaved.setImage(newFileName);
			fournisseurToBeSaved.setPatente(saveFile(patente));
			savedFournisseur = fournisseurService.saveFour(fournisseurToBeSaved);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(savedFournisseur);
	}

	@GetMapping(path = "/logoFournisseur/{id}")
	public ResponseEntity getLogo(@PathVariable("id") Long id) throws Exception {
		Fournisseur fournisseur = null;		
		try {
			fournisseur = fournisseurRepository.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(Files.readAllBytes(Paths.get(imagesDirectory+"\\" +
				fournisseur.getImage())));
	}
	
	@GetMapping(path = "/patenteFournisseur/{id}")
	public ResponseEntity getPatente(@PathVariable("id") Long id) throws Exception {
		Fournisseur fournisseur = null;		
		try {
			fournisseur = fournisseurRepository.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(Files.readAllBytes(Paths.get(imagesDirectory+"\\" +
				fournisseur.getPatente())));
	}

	@PutMapping("/updateFournisseurWithLogo")
	public ResponseEntity updateFournisseurPhoto(@RequestParam("file") MultipartFile file,
			@RequestParam("fournisseur") String fournisseur)
			throws JsonParseException, JsonMappingException, Exception {
		Fournisseur fournisseurToBeUpdated =null;
		String newFileName =null;
		try {
			fournisseurToBeUpdated = new ObjectMapper().readValue(fournisseur, Fournisseur.class);
			boolean isExit = new File(imagesDirectory).exists();
			if (!isExit) {
				new File(imagesDirectory).mkdir();
			}
			newFileName = saveFile(file);
			fournisseurToBeUpdated.setImage(newFileName);
			fournisseurToBeUpdated = updateAndMapFournisseur(fournisseurToBeUpdated.getIduser(), fournisseurToBeUpdated);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(fournisseurToBeUpdated);
	}
	
	@PutMapping("/updateFournisseurWithPatente")
	public ResponseEntity updateFournisseurPatente(@RequestParam("patente") MultipartFile patente,
			@RequestParam("fournisseur") String fournisseur)
			throws JsonParseException, JsonMappingException, Exception {
		Fournisseur fournisseurToBeUpdated =null;
		String newFileName =null;
		try {
			fournisseurToBeUpdated = new ObjectMapper().readValue(fournisseur, Fournisseur.class);
			boolean isExit = new File(imagesDirectory).exists();
			if (!isExit) {
				new File(imagesDirectory).mkdir();
			}
			newFileName = saveFile(patente);
			fournisseurToBeUpdated.setPatente(newFileName);
			fournisseurToBeUpdated = updateAndMapFournisseur(fournisseurToBeUpdated.getIduser(), fournisseurToBeUpdated);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(fournisseurToBeUpdated);
	}
	
	@PutMapping("/changePassword")
	public ResponseEntity changePassword(@RequestBody ChangePasswordPayload changePasswordPayload) {	
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user0 = userRepository.findByUsername(auth.getName());
			Long id0 = (long) user0.getIduser();
			fournisseurService.changePassWord(id0, changePasswordPayload.getPassword());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Password Changed");

	}

	private Fournisseur updateAndMapFournisseur(Long idFournisseur, Fournisseur fournisseurUpdated) {
		return fournisseurRepository.findById(idFournisseur).map(fournisseur -> {
			fournisseur.setNom_societe(fournisseurUpdated.getNom_societe());
			fournisseur.setNom_f(fournisseurUpdated.getNom_f());
			fournisseur.setPrenom_f(fournisseurUpdated.getPrenom_f());
			fournisseur.setDate_fin_contrat(fournisseurUpdated.getDate_fin_contrat());
			fournisseur.setAdresse_societe(fournisseurUpdated.getAdresse_societe());
			fournisseur.setGouvernorat_societe(fournisseurUpdated.getGouvernorat_societe());
			fournisseur.setLocalite_societe(fournisseurUpdated.getLocalite_societe());
			fournisseur.setDelegation_societe(fournisseurUpdated.getDelegation_societe());
			fournisseur.setAdresse_livraison(fournisseurUpdated.getAdresse_livraison());
			fournisseur.setGouvernorat_livraison(fournisseurUpdated.getGouvernorat_livraison());
			fournisseur.setLocalite_livraison(fournisseurUpdated.getLocalite_livraison());
			fournisseur.setDelegation_livraison(fournisseurUpdated.getDelegation_livraison());
			fournisseur.setPrix_livraison(fournisseurUpdated.getPrix_livraison());
			fournisseur.setPrix_retour(fournisseurUpdated.getPrix_retour());
			//fournisseur.setColis(fournisseurUpdated.getColis());
			fournisseur.setEmail(fournisseurUpdated.getEmail());
			fournisseur.setTel(fournisseur.getTel());
			if(fournisseurUpdated.getImage()!=null) {
				fournisseur.setImage(fournisseurUpdated.getImage());
			}
			if(fournisseurUpdated.getPatente()!=null) {
				fournisseur.setPatente(fournisseurUpdated.getPatente());
			}
			return fournisseurRepository.save(fournisseur);
		}).orElseThrow(() -> new AbortException("Fournisseur inexistant"));
	}
	private String saveFile(MultipartFile file) throws IOException {
		String filename = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
		File serverFile = new File(imagesDirectory+"\\"+ File.separator + newFileName);
		FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
		return newFileName;
	}
}
