package com.example.filedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.filedemo.model.SocietePrincipal;
import com.example.filedemo.service.SocietePrincipalService;

@Controller
public class SocietePrincipalController {
    @Autowired
    SocietePrincipalService societePrincipalService;
    
	@PostMapping(value = "/configureSocietePrincipal")
	public ResponseEntity configureSocietePrincipal(@RequestBody SocietePrincipal societePrincipal) {
		SocietePrincipal societePrincipalPostSave = null;
		try {
			societePrincipalPostSave=societePrincipalService.configureSocietePrincipal(societePrincipal);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(societePrincipalPostSave);
	}
	
	@PutMapping(value = "/updateSocietePrincipal")
	public ResponseEntity updateSocietePrincipal(@RequestBody SocietePrincipal societePrincipal) {
		SocietePrincipal societePrincipalPostUpdate = null;
		try {
			societePrincipalPostUpdate=societePrincipalService.updateSocietePrincipal(societePrincipal);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(societePrincipalPostUpdate);
	}
	
	@GetMapping(value = "/initializeCompanyInfos")
	public ResponseEntity initializeCompanyInfos() {
		SocietePrincipal societePrincipal = null;
		try {
			societePrincipal=societePrincipalService.retrieveConfigSocietePrincipal();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(societePrincipal);
	}

}
