package com.example.filedemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.filedemo.model.Debrief;
import com.example.filedemo.payload.DebriefPayload;
import com.example.filedemo.service.DebriefService;


@Controller
public class DebriefController {
	@Autowired
	private DebriefService debriefService;
	
	@GetMapping(value = "/checkDebriefEligibility/{idLivreur}")
	public ResponseEntity findColisEchange(@PathVariable("idLivreur") Long idLivreur) {
		Boolean isEligible =new Boolean(false);
		try {
			isEligible = debriefService.checkDebriefEligibility(idLivreur);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(isEligible);
	}
	
	@GetMapping(value = "/findAllDebrief")
	public ResponseEntity findAllDebrief() {
		List<Debrief> debriefs= new ArrayList<>();
		try {
			debriefs = debriefService.getAllDebrief();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(debriefs);
	}
	
	@GetMapping(value = "/findAllDebriefByIdValidator/{idValidator}")
	public ResponseEntity findAllDebriefByIdValidator(@PathVariable("idValidator") Long idValidator ) {
		List<Debrief> debriefs= new ArrayList<>();
		try {
			debriefs = debriefService.getAllDebriefByIdValidator(idValidator);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(debriefs);
	}
	@GetMapping(value = "/findAllDebriefByIdLivreur/{idLivreur}")
	public ResponseEntity findAllDebriefByIdLivreur(@PathVariable("idLivreur") Long idLivreur ) {
		List<Debrief> debriefs= new ArrayList<>();
		try {
			debriefs = debriefService.getAllDebriefByIdLivreur(idLivreur);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(debriefs);
	}
	
	@PostMapping(value = "/createDebrief")
	public ResponseEntity createDebrief(@RequestBody DebriefPayload debriefPayload ) {
		Debrief debrief= null;
		try {
			debrief = debriefService.createDebrief(debriefPayload);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(debrief);
	}
}
