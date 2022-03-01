package com.example.filedemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.filedemo.model.Hub;
import com.example.filedemo.model.Personnel;
import com.example.filedemo.payload.HubPayload;
import com.example.filedemo.service.HubService;
import com.example.filedemo.service.PersonnelService;

@RestController
public class HubController {

	@Autowired
    HubService hubservice ;
	
    @GetMapping("/retrieve-all-Hubs")
    @ResponseBody
    public ResponseEntity getHub() {
        List<Hub> list = new ArrayList<>();
    	try {
    		list = hubservice.retrieveAllHubs();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.OK).body(list) ;
    }

    @GetMapping("/retrieve-Hub/{Hub-id}")
    @ResponseBody
    public ResponseEntity retrieveHub(@PathVariable("Hub-id") Long id) {
        Hub hub=null;
    	try {
    		hub= hubservice.retrieveHub(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.OK).body(hub) ;
    }

    @PostMapping("/add-Hub")
    @ResponseBody
    public ResponseEntity addHub(@RequestBody Hub hub) {
    	try {
    		hub= hubservice.addHub(hub);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.CREATED).body(hub) ;
    }



    @DeleteMapping("/removeHub/{id}")
    @ResponseBody
    public ResponseEntity removeHub (@PathVariable("id") Long id) {
    	ArrayList<String> barcodes=new ArrayList<>();
    	try {
    		barcodes=(ArrayList<String>) hubservice.deleteHub(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
    	HttpStatus status = barcodes.size()==0? HttpStatus.OK: HttpStatus.BAD_REQUEST;
    	return ResponseEntity.status(status).body(barcodes) ;
    }

    @PutMapping("/modify-Hub")
    @ResponseBody
    public ResponseEntity modifyPersonnel(@RequestBody Hub hub) {        
    	try {
    		hub= hubservice.updateHub(hub);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
    	return ResponseEntity.status(HttpStatus.OK).body(hub) ;
    }
    
    @PutMapping("/updateGovernoratsLiesHub")
    @ResponseBody
    public ResponseEntity updateGovernoratsLiesHub(@RequestBody HubPayload hubPayload) {
    	Hub hub = null;
    	try {
    		hub=hubservice.updateGovernoratsLiesHub(hubPayload);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(hub);
    }
}
